import useModal from "@/shared/hook/useModal";
import FilterModal from "@/domain/student/component/search/FilterModal";
import FilterButton from "@/domain/student/component/search/FilterButton";
import type { FilterState } from "@/domain/student/page/course/SearchResultPage";

interface FilterBarProps {
  filterState: FilterState;
  onFilterChange: (filter: FilterState) => void;
}

const FilterBar = ({ filterState, onFilterChange }: FilterBarProps) => {
  const { modalRef, openModal, closeModal } = useModal();
  const hasFilter: boolean = Boolean(
    filterState.category || filterState.subCategories.length > 0
  );

  return (
    <div className="flex items-center gap-2">
      <FilterButton onClick={openModal} isActive={hasFilter} />
      {hasFilter && (
        <>
          {filterState.category && (
            <span className="flex flex-col items-center justify-start gap-[2.5rem] rounded-full border-1 border-main-500 bg-white px-[1.2rem] py-2 text-sm font-bold text-main-600 opacity-90">
              {filterState.category}
            </span>
          )}
          {filterState.subCategories.map((sub, i) => (
            <span
              className="flex flex-col items-center justify-start gap-[2.5rem] rounded-full border-1 border-main-500 bg-white px-[1.2rem] py-2 text-sm font-bold text-main-600 opacity-90"
              key={i}
            >
              {sub}
            </span>
          ))}
        </>
      )}
      <FilterModal
        modalRef={modalRef}
        closeModal={closeModal}
        filterState={filterState}
        onFilterChange={onFilterChange}
      />
    </div>
  );
};

export default FilterBar;
