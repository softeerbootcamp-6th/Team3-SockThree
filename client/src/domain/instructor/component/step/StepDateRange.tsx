import { useState } from "react";
import { DatePicker } from "@/domain/instructor/component/DatePicker.tsx";

interface StepDateRangeProps {
  value?: {
    startDate?: Date;
    endDate?: Date;
  };
  onValidSubmit: (value: { startDate: Date; endDate: Date }) => void;
}

const StepDateRange = ({ value, onValidSubmit }: StepDateRangeProps) => {
  const [startDate, setStartDate] = useState<Date | undefined>(
    value?.startDate
  );
  const [endDate, setEndDate] = useState<Date | undefined>(value?.endDate);

  const handleSelect = (field: "startDate" | "endDate", date: Date) => {
    if (field === "startDate") {
      setStartDate(date);
    } else {
      setEndDate(date);
    }

    const nextStart = field === "startDate" ? date : startDate;
    const nextEnd = field === "endDate" ? date : endDate;

    if (nextStart && nextEnd) {
      onValidSubmit({ startDate: nextStart, endDate: nextEnd });
    }
  };

  return (
    <div className="flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌 진행 기간을 설정해주세요</p>
      <div className="flex items-center gap-2.5">
        <DatePicker
          id="start-date"
          placeholder="강좌 시작일을 선택하세요"
          value={startDate}
          onChange={(date) => handleSelect("startDate", date)}
        />
        <p>~</p>
        <DatePicker
          id="end-date"
          placeholder="강좌 종료일을 선택하세요"
          value={endDate}
          onChange={(date) => handleSelect("endDate", date)}
        />
      </div>
    </div>
  );
};

export default StepDateRange;
