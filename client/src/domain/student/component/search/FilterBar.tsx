import useModal from "@/shared/hook/useModal";
import FilterModal from "@/domain/student/component/search/FilterModal";
import FilterButton from "@/domain/student/component/search/FilterButton";

interface FilterBarProps {
  selectedCategory?: string;
  selectedSubcategories?: string[];
}

const FilterBar = ({
  selectedCategory,
  selectedSubcategories = [],
}: FilterBarProps) => {
  const { modalRef, openModal, closeModal } = useModal();
  const hasFilter = selectedCategory || selectedSubcategories.length > 0;

  return (
    <div>
      <FilterButton onClick={openModal} />
      {hasFilter && (
        <>
          {selectedCategory && <span>{selectedCategory}</span>}
          {selectedSubcategories.map((sub) => (
            <span>{sub}</span>
          ))}
        </>
      )}
      <FilterModal modalRef={modalRef} closeModal={closeModal} />
    </div>
  );
};

export default FilterBar;
