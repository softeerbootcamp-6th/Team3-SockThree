import SearchBar from "@/domain/student/component/search/SearchBar";
import FilterBar from "@/domain/student/component/search/FilterBar";
import { useCallback, useEffect, useState } from "react";
import { useSearchParams } from "react-router";
import SortBar from "@/domain/student/component/search/SortBar";

export type FilterState = {
  category?: string;
  subCategories: string[];
  // 추가 될 수 있음
};

const SearchResultPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();

  const [searchQuery, setSearchQuery] = useState<string>("");
  const [filterState, setFilterState] = useState<FilterState>({
    category: undefined,
    subCategories: [],
  });

  useEffect(() => {
    const query = searchParams.get("q") || "";
    const category = searchParams.get("category") || undefined;
    const subCategories =
      searchParams.get("subcategories")?.split(",").filter(Boolean) || [];

    setSearchQuery(query);
    setFilterState({ category, subCategories });
  }, [searchParams]);

  const updateSearchParams = useCallback(
    (query: string, filter: FilterState) => {
      const params = new URLSearchParams();
      if (params.toString() !== searchParams.toString()) {
        setSearchParams(params);
      }

      if (query.trim()) params.set("q", query);

      if (filter.category) params.set("category", filter.category);

      if (filter.subCategories.length > 0)
        params.set("subcategories", filter.subCategories.join(","));

      setSearchParams(params);
    },
    [setSearchParams]
  );

  const handleSearchInputChange = (q: string) => {
    setSearchQuery(q);
    updateSearchParams(q, filterState);
  };

  const handleCategoryChange = (newFilter: FilterState) => {
    setFilterState(newFilter);
    updateSearchParams(searchQuery, newFilter);
  };

  return (
    <div className="flex w-full flex-col gap-[2rem] pr-[10rem]">
      <SearchBar value={searchQuery ?? ""} onChange={handleSearchInputChange} />
      <FilterBar
        filterState={filterState}
        onFilterChange={handleCategoryChange}
      />
      <div className="flex justify-end">
        <SortBar />
      </div>
      {/*    검색 결과 */}
      <div className="flex min-h-[10rem] w-full flex-col gap-[2rem] bg-amber-200"></div>
    </div>
  );
};

export default SearchResultPage;
