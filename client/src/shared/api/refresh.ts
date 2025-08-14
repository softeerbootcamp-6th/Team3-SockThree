import { token } from "@/shared/api/token";
import { ApiError } from "@/shared/api/error";

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
      await refreshAccessToken();
      return fn();
    }
    throw err;
  }
};
