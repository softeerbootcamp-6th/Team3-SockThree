import { useState } from "react";
import Chips from "@/shared/components/Chips.tsx";

interface StepSubCategoryProps {
  category: string;
  onNextStep: (context: { subCategory: string }) => void;
}

const subCategoryOptions: Record<string, string[]> = {
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
  // ...카테고리별 하위 항목 추가 가능
};

const StepSubCategory = ({ category, onNextStep }: StepSubCategoryProps) => {
  const [selected, setSelected] = useState<string | null>(null);

  const options = subCategoryOptions[category] ?? ["직접 입력"];

  const handleClick = (subCategory: string) => {
    setSelected(subCategory);
    onNextStep({ subCategory });
  };

  return (
    <div className="border- flex w-[71rem] flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">어떤 [{category}] 강좌를 만들까요?</p>
      <div className="flex flex-wrap gap-2">
        {options.map((option) => (
          <Chips
            key={option}
            type="field"
            title={option}
            selected={selected === option}
            onClick={() => handleClick(option)}
          />
        ))}
      </div>
    </div>
  );
};

export default StepSubCategory;
