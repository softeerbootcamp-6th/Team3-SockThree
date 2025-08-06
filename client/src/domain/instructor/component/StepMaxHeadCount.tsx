import MaxHeadCountSlider from "@/domain/instructor/component/Slider.tsx";
import { useFormContext } from "react-hook-form";

interface StepMaxHeadCountProps {
  onNext: () => void;
}

const StepMaxHeadCount = ({ onNext }: StepMaxHeadCountProps) => {
  // 초기 최대 인원수 (서버에서 fetch 해올 값)
  const INIT_COUNT = 35;
  const { watch, setValue, trigger } = useFormContext();

  const maxHeadCount = watch("maxHeadCount") || INIT_COUNT;

  const handleChange = (value: number) => {
    setValue("maxHeadCount", value);
  };

  const handleNext = async () => {
    const isValid = await trigger("maxHeadCount");
    if (isValid) {
      onNext();
    }
  };

  return (
    <div className="border- flex w-[71rem] flex-col gap-[90px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">최대 몇 명까지 수강할 수 있나요?</p>
      <div className="flex flex-col gap-6">
        <div className="w-full max-w-xl space-y-4">
          <div className="flex items-end gap-2"></div>
          <MaxHeadCountSlider value={maxHeadCount} onChange={handleChange} />
        </div>
        <button
          onClick={handleNext}
          disabled={!maxHeadCount || maxHeadCount <= 0}
          className="rounded-lg bg-blue-500 px-4 py-2 text-white disabled:bg-gray-300"
        >
          선택
        </button>
      </div>
    </div>
  );
};

export default StepMaxHeadCount;
