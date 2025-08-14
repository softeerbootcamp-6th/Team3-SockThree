import useModal from "@/shared/hook/useModal";
import FilterModal from "@/domain/student/component/search/FilterModal";
import FilterButton from "@/domain/student/component/search/FilterButton";
import type { FilterState } from "@/domain/student/page/course/SearchResultPage.tsx";

interface FilterBarProps {
  filterState: FilterState;
  onFilterChange: (filter: FilterState) => void;
}

const FilterBar = ({ filterState, onFilterChange }: FilterBarProps) => {
  const { modalRef, openModal, closeModal } = useModal();
  const hasFilter =
    filterState.category || filterState.subCategories.length > 0;

  return (
    <div className="flex items-center gap-2">
      <FilterButton onClick={openModal} />
      {hasFilter && (
        <>
          {filterState.category && <span>{filterState.category}</span>}
          {filterState.subCategories.map((sub, i) => (
            <span key={i}>{sub}</span>
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
