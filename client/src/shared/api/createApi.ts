import { request } from "@/shared/api/http";

export function createApi(basePath: string) {
  return {
    get: <TRes>(
      path = "",
      query?: Record<string, string | number | boolean | undefined>
    ) => request<TRes>({ method: "GET", path: `${basePath}${path}`, query }),
    post: <TRes, TBody>(path = "", body?: TBody) =>
      request<TRes, TBody>({
        method: "POST",
        path: `${basePath}${path}`,
        body,
      }),
    patch: <TRes, TBody>(path = "", body?: TBody) =>
      request<TRes, TBody>({
        method: "PATCH",
        path: `${basePath}${path}`,
        body,
      }),
    put: <TRes, TBody>(path = "", body?: TBody) =>
      request<TRes, TBody>({ method: "PUT", path: `${basePath}${path}`, body }),
    del: <TRes>(path = "") =>
      request<TRes>({ method: "DELETE", path: `${basePath}${path}` }),
  };
}
