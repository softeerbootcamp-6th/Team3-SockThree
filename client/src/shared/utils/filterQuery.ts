import type { FilterState } from "@/domain/student/page/course/SearchResultPage";

export const parseFilterState = (params: URLSearchParams): FilterState => {
  return {
    query: params.get("q") || undefined,
    category: params.get("category") || undefined,
    subCategories:
      params.get("subcategories")?.split(",").filter(Boolean) || [],
    sort: params.get("sort") || undefined,
  };
};

export const serializeFilterState = (filter: FilterState): URLSearchParams => {
  const params = new URLSearchParams();

  if (filter.query) {
    params.set("q", filter.query);
  }
  if (filter.category) {
    params.set("category", filter.category);
  }

  if (filter.subCategories.length > 0) {
    params.set("subcategories", filter.subCategories.join(","));
  }

  if (filter.sort) {
    params.set("sort", filter.sort);
  }

  return params;
};
