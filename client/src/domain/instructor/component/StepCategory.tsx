import { useState } from "react";
import Chips from "@/shared/components/Chips.tsx";

const StepCategory = ({
  onNextStep,
}: {
  onNextStep: (context: { category: string }) => void;
}) => {
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
  const [selected, setSelected] = useState<string | null>(null);

  const handleClick = (category: string) => {
    setSelected(category);
    onNextStep({ category });
  };

  return (
    <div className="border- flex w-[71rem] flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">어떤 강좌를 만들까요?</p>
      <div className="flex gap-1.5">
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

export default StepCategory;
