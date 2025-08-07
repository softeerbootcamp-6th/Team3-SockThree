import { DatePicker } from "@/domain/instructor/component/DatePicker.tsx";
import { useFormContext } from "react-hook-form";

interface StepDateRangeProps {
  onNext: () => void;
}

const StepDateRange = ({ onNext }: StepDateRangeProps) => {
  const { watch, setValue, trigger } = useFormContext();

  const startDate = watch("startDate");
  const endDate = watch("endDate");

  const handleClickStart = (value: Date) => {
    setValue("startDate", value.toDateString());
  };

  const handleClickEnd = (value: Date) => {
    setValue("endDate", value.toDateString());
  };

  const handleNext = async () => {
    const isValid = await trigger(["startDate", "endDate"]);
    if (isValid) {
      onNext();
    }
  };

  return (
    <div className="border- flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌 진행 기간을 설정해주세요</p>
      <div className="flex items-center gap-2.5">
        <DatePicker
          id="start-date"
          placeholder="강좌 시작일을 선택하세요"
          value={startDate}
          onChange={handleClickStart}
        />
        <p>~</p>
        <DatePicker
          id="end-date"
          placeholder="강좌 종료일을 선택하세요"
          value={endDate}
          onChange={handleClickEnd}
        />
        <button
          onClick={handleNext}
          disabled={!startDate || !endDate}
          className="rounded-2xl border border-blue-500 px-8 py-4 text-gray-900"
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default StepDateRange;
