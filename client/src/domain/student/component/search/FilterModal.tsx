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
      <div className="flex min-h-[30rem] min-w-[40rem] flex-col justify-between bg-gray-100 p-[3rem]">
        <div className="flex flex-col gap-[1rem]">
          <label className="typo-body-2">대분류</label>
          <div className="flex flex-wrap gap-2">
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
            <>
              <label className="typo-body-2">소분류</label>
              <div className="flex flex-wrap gap-2">
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
            </>
          )}
        </div>
        <div className="flex justify-end gap-2">
          <button
            className="cursor-pointer rounded-md bg-gray-200 px-[1rem] py-[0.6rem] text-gray-600 shadow-gray-200"
            onClick={handleCancel}
          >
            취소
          </button>
          <button
            className="cursor-pointer rounded-md bg-main-500 px-[1rem] py-[0.6rem] text-white shadow-gray-200"
            onClick={handleApply}
          >
            적용
          </button>
        </div>
      </div>
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
