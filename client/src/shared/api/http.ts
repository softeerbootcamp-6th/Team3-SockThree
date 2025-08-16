// src/shared/api/client/http.ts
import { token } from "@/shared/api/token";
import { ApiError } from "@/shared/api/error";
import { withAutoRefresh } from "@/shared/api/refresh";

type RequestOptions<TBody = unknown> = {
  method?: "GET" | "POST" | "PUT" | "PATCH" | "DELETE";
  path: string; // 상대 경로
  query?: Record<string, string | number | boolean | undefined>;
  body?: TBody; // JSON 형태의 요청 본문
  headers?: Record<string, string>;
  rawBody?: BodyInit; // JSON 외(FormData, Blob 등)
  signal?: AbortSignal; // 요청 취소용
};

const buildUrl = (path: string, query?: RequestOptions["query"]) => {
  const base = import.meta.env.VITE_API_BASE_URL;
  const url = new URL(path, base);
  if (query) {
    Object.entries(query).forEach(([k, v]) => {
      if (v !== undefined) {
        url.searchParams.set(k, String(v));
      }
    });
  }
  return url.toString();
};

const _request = async <TRes, TBody = unknown>(
  opts: RequestOptions<TBody>
): Promise<TRes> => {
  const { method = "GET", path, query, body, headers, rawBody } = opts;
  const url = buildUrl(path, query);
  const access = token.get();

  const finalHeaders: Record<string, string> = {
    ...(rawBody ? {} : { "Content-Type": "application/json" }),
    ...(access ? { Authorization: `Bearer ${access}` } : {}),
    ...headers,
  };

  const res = await fetch(url, {
    method,
    credentials: "include", // refresh 쿠키 포함
    headers: finalHeaders,
    body: rawBody ?? (body ? JSON.stringify(body) : undefined),
  });

  const payload = res.status !== 204 ? await res.json() : null;

  if (!res.ok) {
    const message = payload?.message || res.statusText || "API Error";
    const code = payload?.code;
    throw new ApiError({ status: res.status, message, code, details: payload });
  }

  return payload as TRes;
};

// 외부 노출: 자동 재발급 포함
export const request = async <TRes, TBody = unknown>(
  opts: RequestOptions<TBody>
) => {
  return withAutoRefresh(() => _request<TRes, TBody>(opts));
};
