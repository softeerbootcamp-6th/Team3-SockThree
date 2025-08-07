import { useFormContext } from "react-hook-form";
import Chips from "@/shared/components/Chips.tsx";

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
  // 다른 카테고리들...
};

const StepSubCategory = ({ onNext }: StepSubCategoryProps) => {
  const {
    watch,
    setValue,
    trigger,
    register,
    formState: { errors },
  } = useFormContext();

  const category = watch("category");
  const selected = watch("subCategory");

  const options = subCategoryOptions[category] ?? ["직접 입력"];

  const handleClick = async (option: string) => {
    setValue("subCategory", option);
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

      {/* Hidden input for RHF */}
      <input
        {...register("subCategory", {
          required: "하위 카테고리를 선택해주세요",
        })}
        type="hidden"
        value={selected || ""}
      />

      {errors.subCategory && (
        <p className="text-sm text-red-500">
          {errors.subCategory.message as string}
        </p>
      )}
    </div>
  );
};

export default StepSubCategory;
