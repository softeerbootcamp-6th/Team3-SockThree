import Modal from "@/shared/components/Modal";
import Chips from "@/shared/components/Chips";
import { useState } from "react";

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
  const [localCategory, setLocalCategory] = useState(selectedCategory ?? "");
  const [localSubcategories, setLocalSubcategories] = useState(
    selectedSubcategories
  );

  const handleCategoryClick = (category: string) => {
    const newCategory = localCategory === category ? "" : category;
    setLocalCategory(newCategory);

    // 카테고리 변경 시 서브카테고리 초기화
    if (newCategory !== localCategory) {
      setLocalSubcategories([]);
    }
  };

  const handleSubcategoryClick = (subcategory: string) => {
    const isSelected = localSubcategories.includes(subcategory);
    const newSubcategories = isSelected
      ? localSubcategories.filter((sub) => sub !== subcategory)
      : [...localSubcategories, subcategory];

    setLocalSubcategories(newSubcategories);
  };

  const handleApply = () => {
    onCategoryChange?.(localCategory || null);
    onSubcategoriesChange?.(localSubcategories);
    onApply?.();
  };

  const handleCancel = () => {
    setLocalCategory(selectedCategory ?? "");
    setLocalSubcategories(selectedSubcategories);
    closeModal();
  };

  return (
    <Modal ref={modalRef}>
      <div>필터 선택하는 곳</div>
      <label>대분류</label>
      <div>
        {categories.map((category) => (
          <Chips
            key={category}
            type="field"
            title={category}
            selected={localCategory === category}
            onClick={() => handleCategoryClick(category)}
          />
        ))}
      </div>
      {localCategory && (
        <div>
          <label>소분류</label>
          <div>
            {subCategories[localCategory]?.map((sub) => (
              <Chips
                key={sub}
                type="field"
                title={sub}
                selected={localSubcategories.includes(sub)}
                onClick={() => handleSubcategoryClick(sub)}
              />
            ))}
          </div>
        </div>
      )}
      <button onClick={handleCancel}>취소</button>
      <button onClick={handleApply}>적용</button>
    </Modal>
  );
};

export default FilterModal;

// constants
const categories = [
  "운동",
  "미술",
  "재테크",
  "디지털 정보화",
  "음악",
  "생활",
  "자격증",
  "기타",
];

const subCategories: Record<string, string[]> = {
  운동: [
    "요가",
    "필라테스",
    "실버에어로빅",
    "탁구",
    "걷기 모임",
    "골프",
    "수영",
    "라인댄스",
    "직접 입력",
  ],
  미술: ["수채화", "유화", "캘리그라피", "색연필화"],
  음악: ["합창", "오카리나", "우쿨렐레", "통기타"],
};
