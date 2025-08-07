import { useFormContext } from "react-hook-form";
import Chips from "@/shared/components/Chips.tsx";

interface StepCategoryProps {
  onNext: () => void;
}

const StepCategory = ({ onNext }: StepCategoryProps) => {
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

  const {
    register,
    trigger,
    formState: { errors },
    watch,
    setValue,
  } = useFormContext();

  const category = watch("category");

  const handleClick = (option: string) => {
    setValue("category", option);
    trigger("category");
    onNext();
  };

  return (
    <div className="border- flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">어떤 강좌를 만들까요?</p>
      <div className="flex gap-1.5">
        {options.map((option) => (
          <Chips
            key={option}
            type="field"
            title={option}
            selected={category === option}
            onClick={() => handleClick(option)}
          />
        ))}
      </div>

      {/* Hidden input for react-hook-form registration */}
      <input
        {...register("category", { required: "카테고리를 선택해주세요" })}
        type="hidden"
        value={category || ""}
      />

      {errors.category && <p className="text-sm text-red-500">문제문제</p>}
    </div>
  );
};

export default StepCategory;
