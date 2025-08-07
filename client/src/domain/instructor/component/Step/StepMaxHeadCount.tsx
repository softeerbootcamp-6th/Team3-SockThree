import { useRef } from "react";
import MaxHeadCountSlider from "@/domain/instructor/component/Slider.tsx";
import { useFormContext } from "react-hook-form";

interface StepMaxHeadCountProps {
  onNext: () => void;
}

const StepMaxHeadCount = ({ onNext }: StepMaxHeadCountProps) => {
  const INIT_COUNT = 35;
  const {
    watch,
    setValue,
    trigger,
    register,
    formState: { errors },
  } = useFormContext();

  const maxHeadCount = watch("maxHeadCount") || INIT_COUNT;

  const debounceRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const handleChange = (value: number) => {
    setValue("maxHeadCount", value);

    // 디바운싱된 유효성 검사 및 onNext
    if (debounceRef.current) {
      clearTimeout(debounceRef.current);
    }

    debounceRef.current = setTimeout(async () => {
      const isValid = await trigger("maxHeadCount");
      if (isValid && value > 0) {
        onNext();
      }
    }, 300);
  };

  return (
    <div className="flex w-full flex-col gap-[90px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">최대 몇 명까지 수강할 수 있나요?</p>

      <div className="flex flex-col gap-6">
        <div className="w-full max-w-xl space-y-4">
          <MaxHeadCountSlider value={maxHeadCount} onChange={handleChange} />
        </div>
      </div>

      {/* RHF 등록 */}
      <input
        {...register("maxHeadCount", {
          required: "최대 인원을 설정해주세요",
          min: { value: 1, message: "1명 이상이어야 합니다" },
        })}
        type="hidden"
        value={maxHeadCount}
      />

      {/* 에러 메시지 */}
      {errors.maxHeadCount && (
        <p className="mt-2 text-sm text-red-500">
          {errors.maxHeadCount.message as string}
        </p>
      )}
    </div>
  );
};

export default StepMaxHeadCount;
