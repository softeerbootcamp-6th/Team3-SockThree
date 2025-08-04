import { useState } from "react";
import { DatePicker } from "@/domain/instructor/component/DatePicker.tsx";

interface StepDateRangeProps {
  onNextStep: (context: { startDate: string; endDate: string }) => void;
}

const StepDateRange = ({ onNextStep }: StepDateRangeProps) => {
  const [startDate, setStartDate] = useState<Date>();
  const [endDate, setEndDate] = useState<Date>();

  const handleSubmit = () => {
    if (startDate && endDate) {
      onNextStep({
        startDate: startDate.toISOString(),
        endDate: endDate.toISOString(),
      });
    }
  };

  return (
    <div className="border- flex w-[71rem] flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌 진행 기간을 설정해주세요</p>
      <div className="flex items-center gap-2.5">
        <DatePicker
          id="start-date"
          placeholder="강좌 시작일을 선택하세요"
          value={startDate}
          onChange={setStartDate}
        />
        <p>~</p>
        <DatePicker
          id="end-date"
          placeholder="강좌 종료일을 선택하세요"
          value={endDate}
          onChange={setEndDate}
        />
        <button
          onClick={handleSubmit}
          disabled={!startDate || !endDate}
          className="rounded-2xl border border-blue-500 px-8 py-4 text-gray-900"
        >
          다음 단계로
        </button>
      </div>
    </div>
  );
};

export default StepDateRange;
