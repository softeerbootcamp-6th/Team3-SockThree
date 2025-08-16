import { token } from "@/shared/api/core/token";
import { ApiError } from "@/shared/api/core/error";
import { redirect } from "react-router";

let refreshingPromise: Promise<string | null> | null = null;

const refreshAccessToken = async () => {
  if (refreshingPromise) return;

  refreshingPromise = (async () => {
    const res = await fetch("refresh_url", {
      method: "POST",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
    });

    if (!res.ok) {
      throw new ApiError({
        status: res.status,
        message: "Failed to refresh token",
      });
    }
    const data: { accessToken: string } = await res.json();
    token.set(data.accessToken);
    return data.accessToken;
  })().finally(() => {
    refreshingPromise = null;
  });
  return refreshingPromise;
};

export const withAutoRefresh = async <T>(fn: () => Promise<T>) => {
  try {
    return await fn();
  } catch (err) {
    const e = err as ApiError;
    if (e instanceof ApiError && e.status === 401) {
      try {
        await refreshAccessToken();
        return fn();
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
      } catch (_refreshError) {
        // 재발급 실패시 login 페이지로 리다이렉트
        token.clear();
        throw redirect("/login");
      }
    }
    throw err;
  }
};
