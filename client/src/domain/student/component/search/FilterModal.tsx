import Modal from "@/shared/components/Modal";
import Chips from "@/shared/components/Chips";
import { useEffect, useState } from "react";
import type { FilterState } from "@/domain/student/page/course/SearchResultPage";

interface FilterModalProps {
  modalRef: React.RefObject<HTMLDialogElement | null>;
  closeModal: () => void;
  filterState: FilterState;
  onFilterChange: (filter: FilterState) => void;
  onApply?: () => void;
}

const FilterModal = ({
  modalRef,
  closeModal,
  filterState,
  onFilterChange,
  onApply,
}: FilterModalProps) => {
  const [localFilter, setLocalFilter] = useState<FilterState>({
    category: filterState.category || "",
    subCategories: filterState.subCategories,
  });

  useEffect(() => {
    setLocalFilter({
      category: filterState.category || "",
      subCategories: filterState.subCategories,
    });
  }, [filterState]);

  const handleCategoryClick = (category: string) => {
    const newCategory = localFilter.category === category ? "" : category;
    setLocalFilter({
      category: newCategory,
      subCategories:
        newCategory !== localFilter.category ? [] : localFilter.subCategories,
    });
  };

  const handleSubcategoryClick = (subCategory: string) => {
    const isSelected = localFilter.subCategories.includes(subCategory);
    const newSubcategories = isSelected
      ? localFilter.subCategories.filter((sub) => sub !== subCategory)
      : [...localFilter.subCategories, subCategory];

    setLocalFilter({
      ...localFilter,
      subCategories: newSubcategories,
    });
  };

  const handleApply = () => {
    console.log(localFilter.subCategories);

    onFilterChange({
      category: localFilter.category || undefined,
      subCategories: localFilter.subCategories,
    });
    onApply?.();
    closeModal();
  };

  const handleCancel = () => {
    setLocalFilter({
      category: filterState.category || "",
      subCategories: filterState.subCategories,
    });
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
                selected={localFilter.category === category}
                onClick={() => handleCategoryClick(category)}
              />
            ))}
          </div>
          {localFilter.category && (
            <>
              <label className="typo-body-2">소분류</label>
              <div className="flex flex-wrap gap-2">
                {subCategories[localFilter.category]?.map((sub) => (
                  <Chips
                    key={sub}
                    type="field"
                    title={sub}
                    selected={localFilter.subCategories.includes(sub)}
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
