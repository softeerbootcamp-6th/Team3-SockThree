import Chips from "@/shared/components/Chips.tsx";
import { useFormContext } from "react-hook-form";

interface StepSubCategoryProps {
  onNext: () => void;
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

const StepSubCategory = ({ onNext }: StepSubCategoryProps) => {
  const {
    watch,
    setValue,
    trigger,
    formState: { errors },
  } = useFormContext();

  const category = watch("category"); // 상위 단계에서 선택된 값
  const selected = watch("subCategory");

  const options = subCategoryOptions[category] ?? ["직접 입력"];

  const handleClick = (value: string) => {
    setValue("subCategory", value);
  };

  const handleNext = async () => {
    const isValid = await trigger("subCategory");
    if (isValid) {
      onNext();
    }
  };

  return (
    <div className="flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
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

export default StepSubCategory;
