import { DatePicker } from "@/domain/instructor/component/DatePicker.tsx";
import { useFormContext } from "react-hook-form";

interface StepDateRangeProps {
  onNext: () => void;
}

const StepDateRange = ({ onNext }: StepDateRangeProps) => {
  const {
    watch,
    setValue,
    trigger,
    register,
    formState: { errors },
  } = useFormContext();

  const startDate = watch("startDate");
  const endDate = watch("endDate");

  const handleSelect = async (field: "startDate" | "endDate", value: Date) => {
    setValue(field, value.toDateString());
    const isValid = await trigger(["startDate", "endDate"]);
    const bothSelected = !!watch("startDate") && !!watch("endDate");

    if (isValid && bothSelected) {
      onNext();
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

      {/* RHF 등록 */}
      <input
        {...register("startDate", { required: "시작일을 선택해주세요" })}
        type="hidden"
        value={startDate || ""}
      />
      <input
        {...register("endDate", { required: "종료일을 선택해주세요" })}
        type="hidden"
        value={endDate || ""}
      />

      {/* 에러 메시지 */}
      {(errors.startDate || errors.endDate) && (
        <p className="mt-2 text-sm text-red-500">
          {(errors.startDate?.message as string) ||
            (errors.endDate?.message as string)}
        </p>
      )}
    </div>
  );
};

export default StepDateRange;
