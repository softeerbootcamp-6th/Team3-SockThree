import SearchBar from "@/domain/student/component/search/SearchBar";
import FilterBar from "@/domain/student/component/search/FilterBar";
import { useSearchParams } from "react-router";
import SortBar from "@/domain/student/component/search/SortBar";
import CourseDetailCard from "@/shared/components/course/CourseDetailCard";
import {
  parseFilterState,
  serializeFilterState,
} from "@/shared/utils/filterQuery";

export type FilterState = {
  query?: string;
  category?: string;
  subCategories: string[];
  sort?: string;
};

const SearchResultPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();

  const filterState: FilterState = parseFilterState<FilterState>(searchParams, [
    "subCategories",
  ]);

  const handleFilterChange = (newFilter: FilterState) => {
    setSearchParams(
      serializeFilterState<FilterState>(newFilter, ["subCategories"]),
      { replace: true }
    );
  };

  return (
    <div className="mt-[4rem] flex w-full max-w-[1290px] flex-col gap-[2rem]">
      <SearchBar
        value={filterState.query || ""}
        onChange={(q) => handleFilterChange({ ...filterState, query: q })}
      />

      <div className="flex justify-between">
        <FilterBar
          filterState={filterState}
          onFilterChange={handleFilterChange}
        />
        <SortBar
          value={filterState.sort || ""}
          onSortChange={(sort) =>
            handleFilterChange({ ...filterState, sort: sort })
          }
        />
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
