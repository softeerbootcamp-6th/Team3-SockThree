import useModal from "@/shared/hook/useModal";
import Modal from "@/shared/components/Modal.tsx";

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
      <Modal ref={modalRef}>
        <div>필터 선택하는 곳</div>
        <div>
          <label>대분류</label>
        </div>
        <div>
          <label>소분류</label>
        </div>
        <button onClick={closeModal}>취소</button>
        <button onClick={closeModal}>적용</button>
      </Modal>
    </div>
  );
};

export default FilterBar;
