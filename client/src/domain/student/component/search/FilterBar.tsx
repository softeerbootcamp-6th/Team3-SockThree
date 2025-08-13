import useModal from "@/shared/hook/useModal";
import FilterModal from "@/domain/student/component/search/FilterModal";
import FilterButton from "@/domain/student/component/search/FilterButton";

interface FilterBarProps {
  selectedCategory?: string;
  selectedSubcategories?: string[];
  onCategoryChange?: (category: string) => void;
  onSubcategoryChange?: (subcategories: string[]) => void;
}

const FilterBar = ({
  selectedCategory,
  selectedSubcategories = [],
  onCategoryChange,
  onSubcategoryChange,
}: FilterBarProps) => {
  const { modalRef, openModal, closeModal } = useModal();
  const hasFilter = selectedCategory || selectedSubcategories.length > 0;

  return (
    <div className="flex items-center gap-2">
      <FilterButton onClick={openModal} />
      {hasFilter && (
        <>
          {selectedCategory && <span>{selectedCategory}</span>}
          {selectedSubcategories.map((sub, i) => (
            <span key={i}>{sub}</span>
          ))}
        </>
      )}
      <FilterModal
        modalRef={modalRef}
        closeModal={closeModal}
        selectedCategory={selectedCategory}
        selectedSubcategories={selectedSubcategories}
        onCategoryChange={onCategoryChange ?? undefined}
        onSubcategoriesChange={onSubcategoryChange ?? undefined}
      />
    </div>
  );
};

export default FilterBar;
