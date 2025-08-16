import { request } from "@/shared/api/core/http";

export function createApi(basePath: string) {
  return {
    get: <TRes>(
      path = "",
      query?: Record<string, string | number | boolean | undefined>,
      opts?: { signal?: AbortSignal; headers?: Record<string, string> }
    ) =>
      request<TRes>({
        method: "GET",
        path: `${basePath}${path}`,
        query,
        signal: opts?.signal,
        headers: opts?.headers,
      }),
    post: <TRes, TBody>(
      path = "",
      body?: TBody | FormData,
      opts?: { signal?: AbortSignal; headers?: Record<string, string> }
    ) =>
      request<TRes, TBody>({
        method: "POST",
        path: `${basePath}${path}`,
        ...(body instanceof FormData ? { rawBody: body } : { body }),
        signal: opts?.signal,
        headers: opts?.headers,
      }),
    patch: <TRes, TBody>(
      path = "",
      body?: TBody,
      opts?: { signal?: AbortSignal; headers?: Record<string, string> }
    ) =>
      request<TRes, TBody>({
        method: "PATCH",
        path: `${basePath}${path}`,
        body,
        signal: opts?.signal,
        headers: opts?.headers,
      }),
    put: <TRes, TBody>(
      path = "",
      body?: TBody | FormData,
      opts?: { signal?: AbortSignal; headers?: Record<string, string> }
    ) =>
      request<TRes, TBody>({
        method: "PUT",
        path: `${basePath}${path}`,
        ...(body instanceof FormData ? { rawBody: body } : { body }),
        signal: opts?.signal,
        headers: opts?.headers,
      }),
    del: <TRes>(
      path = "",
      opts?: { signal?: AbortSignal; headers?: Record<string, string> }
    ) =>
      request<TRes>({
        method: "DELETE",
        path: `${basePath}${path}`,
        signal: opts?.signal,
        headers: opts?.headers,
      }),
  };
}
