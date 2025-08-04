import { useState } from "react";
import Chips from "@/shared/components/Chips.tsx";

interface StepMaxHeadCountProps {
  onNextStep: (context: { maxHeadCount: number }) => void;
}

const StepMaxHeadCount = ({ onNextStep }: StepMaxHeadCountProps) => {
  const [maxHeadCount, setMaxHeadCount] = useState<number | null>(null);
  const [customCount, setCustomCount] = useState("");

  const predefinedOptions = [5, 10, 15, 20];

  const handlePredefinedClick = (count: number) => {
    setMaxHeadCount(count);
    setCustomCount("");
    onNextStep({ maxHeadCount: count });
  };

  const handleCustomSubmit = () => {
    const count = parseInt(customCount);
    if (count > 0) {
      setMaxHeadCount(count);
      onNextStep({ maxHeadCount: count });
    }
  };

  return (
    <div className="border- flex w-[71rem] flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">최대 몇 명까지 수강할 수 있나요?</p>
      <div className="flex flex-col gap-6">
        <div className="flex flex-wrap gap-4">
          {predefinedOptions.map((count) => (
            <Chips
              key={count}
              type="field"
              title={`${count}명`}
              selected={maxHeadCount === count}
              onClick={() => handlePredefinedClick(count)}
            />
          ))}
        </div>
        <div className="flex items-center gap-4">
          <span>직접 입력:</span>
          <input
            type="number"
            value={customCount}
            onChange={(e) => setCustomCount(e.target.value)}
            placeholder="인원수 입력"
            className="rounded-lg border px-4 py-2"
            min="1"
          />
          <button
            onClick={handleCustomSubmit}
            disabled={!customCount || parseInt(customCount) <= 0}
            className="rounded-lg bg-blue-500 px-4 py-2 text-white disabled:bg-gray-300"
          >
            선택
          </button>
        </div>
      </div>
    </div>
  );
};

export default StepMaxHeadCount;
