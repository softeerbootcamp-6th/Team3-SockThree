import useModal from "@/shared/hook/useModal";
import Modal from "@/shared/components/Modal.tsx";
import FilterModal from "@/domain/student/component/search/FilterModal.tsx";

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
      <button onClick={openModal}>필터</button>
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
