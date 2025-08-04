import { useState } from "react";
import Chips from "@/shared/components/Chips.tsx";

interface StepLevelProps {
  subCategory: string;
  onNextStep: (context: { level: string }) => void;
}

const levelOptions = [
  {
    title: "입문",
    description: "이 분야를 처음 배우는 사람을 위한 단계예요.",
  },
  {
    title: "초급",
    description: "아주 쉬운 내용부터 천천히 익혀요.",
  },
  {
    title: "중급",
    description: "기본은 알고, 조금 더 배워가는 단계예요.",
  },
  {
    title: "고급",
    description: "대부분 잘 알고, 깊이 있게 배우는 단계예요.",
  },
  {
    title: "전문가",
    description: "거의 다 알고, 타인에게도 알려줄 수 있어요.",
  },
];

const StepLevel = ({ onNextStep, subCategory }: StepLevelProps) => {
  const [selected, setSelected] = useState("");

  const handleClick = (level: string) => {
    setSelected(level);
    onNextStep({ level });
  };

  return (
    <div className="border- flex w-[71rem] flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">
        [{subCategory}] 강좌의 난이도를 선택해주세요
      </p>
      <div className="flex flex-wrap gap-4">
        {levelOptions.map(({ title, description }) => (
          <Chips
            key={title}
            type="level"
            title={title}
            description={description}
            selected={selected === title}
            onClick={() => handleClick(title)}
          />
        ))}
      </div>
    </div>
  );
};

export default StepLevel;
