import Modal from "@/shared/components/Modal.tsx";

interface FilterModalProps {
  modalRef: React.RefObject<HTMLDialogElement>;
  closeModal: () => void;

  selectedCategory?: string;
  selectedSubcategories?: string[];

  onCategoryChange?: (category: string | null) => void;
  onSubcategoriesChange?: (subcategories: string[]) => void;

  onApply?: () => void;
}

const FilterModal = ({
  modalRef,
  closeModal,
  selectedCategory,
  selectedSubcategories = [],
  onCategoryChange,
  onSubcategoriesChange,
  onApply,
}: FilterModalProps) => {
  return (
    <Modal ref={modalRef}>
      <div>필터 선택하는 곳</div>
      <div>
        <label>대분류</label>
      </div>
      <div>
        <label>소분류</label>
      </div>
      <button onClick={closeModal}>취소</button>
      <button onClick={onApply}>적용</button>
    </Modal>
  );
};

export default FilterModal;
