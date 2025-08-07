import Chips from "@/shared/components/Chips.tsx";
import { useState } from "react";

const options = [
  "운동",
  "미술",
  "재테크",
  "디지털 정보화",
  "음악",
  "생활",
  "자격증",
  "기타",
];

interface StepCategoryProps {
  value?: string;
  onValidSubmit: (value: string) => void;
}

const StepCategory = ({ value, onValidSubmit }: StepCategoryProps) => {
  const [localValue, setLocalValue] = useState(value ?? "");

  const handleClick = (option: string) => {
    setLocalValue(option);
    onValidSubmit(option); // 직접 클릭한 값을 넘김
  };
  return (
    <div className="flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">어떤 강좌를 만들까요?</p>
      <div className="flex flex-wrap gap-2">
        {options.map((option) => (
          <Chips
            key={option}
            type="field"
            title={option}
            selected={localValue === option}
            onClick={() => handleClick(option)}
          />
        ))}
      </div>
    </div>
  );
};

export default StepCategory;
