/* eslint-disable @typescript-eslint/no-explicit-any */
export const parseFilterState = <
  T extends Record<string, string | string[] | undefined>,
>(
  params: URLSearchParams,
  arrayKeys: (keyof T)[]
): T => {
  const state = {} as T;

  arrayKeys.forEach((key) => {
    (state as any)[key] = [];
  });

  params.forEach((value, key) => {
    if (arrayKeys.includes(key as keyof T)) {
      (state as any)[key] = value.split(",").filter(Boolean);
    } else {
      (state as any)[key] = value || undefined;
    }
  });

  return state;
};

export const serializeFilterState = <
  T extends Record<string, string | string[] | undefined>,
>(
  filter: T,
  arrayKeys: (keyof T)[]
): URLSearchParams => {
  const params = new URLSearchParams();

  Object.entries(filter).forEach(([key, value]) => {
    if (value === undefined) return;

    if (arrayKeys.includes(key as keyof T)) {
      const arr = value as string[];
      if (arr.length > 0) {
        params.set(key, arr.join(","));
      }
    } else {
      params.set(key, value as string);
    }
  });

  return params;
};
