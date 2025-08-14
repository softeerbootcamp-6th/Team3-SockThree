import SearchBar from "@/domain/student/component/search/SearchBar";
import FilterBar from "@/domain/student/component/search/FilterBar";
import { useCallback, useEffect, useState } from "react";
import { useSearchParams } from "react-router";
import SortBar from "@/domain/student/component/search/SortBar";
import CourseDetailCard from "@/shared/components/course/CourseDetailCard";

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

  const [sortBy, setSortBy] = useState<string>("");

  useEffect(() => {
    const query = searchParams.get("q") || "";
    const category = searchParams.get("category") || undefined;
    const subCategories =
      searchParams.get("subcategories")?.split(",").filter(Boolean) || [];
    const sort = searchParams.get("sort") || "";

    setSearchQuery(query);
    setFilterState({ category, subCategories });
    setSortBy(sort);
  }, [searchParams]);

  const updateSearchParams = useCallback(
    (query: string, filter: FilterState, sort?: string) => {
      const params = new URLSearchParams();
      if (params.toString() !== searchParams.toString()) {
        setSearchParams(params);
      }

      if (query.trim()) params.set("q", query);

      if (filter.category) params.set("category", filter.category);

      if (filter.subCategories.length > 0)
        params.set("subcategories", filter.subCategories.join(","));

      if (sort && sort.trim()) params.set("sort", sort);
      setSearchParams(params);
    },
    [setSearchParams]
  );

  const handleSearchInputChange = (q: string) => {
    setSearchQuery(q);
    updateSearchParams(q, filterState, sortBy);
  };

  const handleCategoryChange = (newFilter: FilterState) => {
    setFilterState(newFilter);
    updateSearchParams(searchQuery, newFilter, sortBy);
  };

  const handleSortChange = (newSort: string) => {
    setSortBy(newSort);
    updateSearchParams(searchQuery, filterState, newSort);
  };

  return (
    <div className="mt-[4rem] flex w-full max-w-[1290px] flex-col gap-[2rem]">
      <SearchBar value={searchQuery ?? ""} onChange={handleSearchInputChange} />

      <div className="flex justify-between">
        <FilterBar
          filterState={filterState}
          onFilterChange={handleCategoryChange}
        />
        <SortBar value={sortBy} onSortChange={handleSortChange} />
      </div>
      {/*    검색 결과 */}
      <div className="grid grid-cols-3 gap-[2rem]">
        {[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((i) => (
          <CourseDetailCard key={i} />
        ))}
      </div>
    </div>
  );
};

export default SearchResultPage;
