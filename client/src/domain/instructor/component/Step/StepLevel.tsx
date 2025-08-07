import Chips from "@/shared/components/Chips.tsx";
import { useFormContext } from "react-hook-form";

interface StepLevelProps {
  onNext: () => void;
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

const StepLevel = ({ onNext }: StepLevelProps) => {
  const {
    watch,
    setValue,
    trigger,
    formState: { errors },
  } = useFormContext();

  const subCategory = watch("subCategory"); // 상위 단계에서 선택된 값
  const selected = watch("level");

  const handleClick = (value: string) => {
    setValue("level", value);
  };

  const handleNext = async () => {
    const isValid = await trigger("subCategory");
    if (isValid) {
      onNext();
    }
  };

  return (
    <div className="border- flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
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
      {errors.subCategory && (
        <p className="text-sm text-red-500">
          {errors.subCategory.message as string}
        </p>
      )}

      <div className="mt-6 flex justify-end">
        <button
          type="button"
          className="rounded bg-blue-500 px-4 py-2 text-white"
          onClick={handleNext}
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default StepLevel;
