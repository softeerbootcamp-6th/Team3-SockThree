import { useFormContext } from "react-hook-form";
import { useEffect, useState } from "react";
import { XIcon } from "lucide-react";
import Chips from "@/shared/components/Chips.tsx";
import { Button } from "@/shared/shadcn/ui/button.tsx";
import Select from "@/shared/components/Select.tsx";

interface StepUploadTimesProps {
  onNext: () => void;
}

const formatHourToKoreanTime = (hour: number): string => {
  if (hour === 0) return "오전 12시";
  if (hour < 12) return `오전 ${hour}시`;
  if (hour === 12) return "오후 12시";
  return `오후 ${hour - 12}시`;
};

const DAYS = ["월", "화", "수", "목", "금", "토", "일"];

const TIME_OPTIONS = Array.from({ length: 24 }, (_, i) => ({
  value: i.toString(),
  label: formatHourToKoreanTime(i),
}));

export default function StepUploadTimes({ onNext }: StepUploadTimesProps) {
  const {
    setValue,
    trigger,
    register,
    watch,
    formState: { errors },
  } = useFormContext();

  const [selectedDay, setSelectedDay] = useState<string | null>(null);
  const [selectedHour, setSelectedHour] = useState<string>("20");

  const uploadTimes: string[] = watch("uploadTimes") || [];

  const handleAddTime = () => {
    if (!selectedDay || !selectedHour) return;
    const timeString = formatHourToKoreanTime(parseInt(selectedHour));
    const newEntry = `${selectedDay}요일 ${timeString}`;
    if (!uploadTimes.includes(newEntry)) {
      const updated = [...uploadTimes, newEntry];
      setValue("uploadTimes", updated, { shouldValidate: true });
    }
  };

  const handleRemoveTime = (entry: string) => {
    const updated = uploadTimes.filter((t) => t !== entry);
    setValue("uploadTimes", updated, { shouldValidate: true });
  };

  const handleNext = async () => {
    const isValid = await trigger("uploadTimes");
    if (isValid) {
      onNext();
    }
  };

  // RHF에 uploadTimes 등록
  useEffect(() => {
    register("uploadTimes", {
      validate: (val: string[]) =>
        val && val.length > 0 ? true : "최소 1개 이상 시간을 추가해주세요",
    });
  }, [register]);

  return (
    <div className="flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">[subcategory] 강좌 업로드는 언제 하시나요?</p>

      <div className="flex gap-36">
        <div className="flex gap-2">
          {DAYS.map((day) => (
            <Chips
              type="day"
              title={day}
              selected={selectedDay === day}
              onClick={() => setSelectedDay(day)}
              key={day}
            />
          ))}
        </div>

        <div className="flex items-center gap-2">
          <Select
            options={TIME_OPTIONS}
            placeholder="시간 선택"
            value={selectedHour}
            onValueChange={setSelectedHour}
          />

          <Button
            variant="outline"
            className="typo-label-2 h-[3.5rem] w-[8.5rem] rounded-lg border border-gray-400 px-3.5 py-2.5 shadow-none hover:text-gray-600"
            onClick={handleAddTime}
          >
            + 시간 추가하기
          </Button>
        </div>
      </div>

      <div className="mt-2 flex flex-wrap gap-2">
        {uploadTimes.map((entry) => (
          <div
            key={entry}
            className="flex items-center gap-1 rounded-full border border-gray-300 bg-white px-3 py-1 text-sm text-black shadow-sm"
          >
            <button
              type="button"
              onClick={() => handleRemoveTime(entry)}
              className="text-gray-400 hover:text-red-500"
            >
              <XIcon size={14} />
            </button>
            {entry}
          </div>
        ))}
      </div>

      {/* 에러 메시지 */}
      {errors.uploadTimes && (
        <p className="mt-2 text-sm text-red-500">
          {errors.uploadTimes.message as string}
        </p>
      )}

      {/* ✅ 업로드 시간이 1개 이상일 때만 표시 */}
      {uploadTimes.length > 0 && (
        <div className="mt-6 flex justify-end">
          <Button
            type="button"
            onClick={handleNext}
            className="rounded-lg bg-blue-500 px-4 py-2 text-white"
          >
            선택 완료
          </Button>
        </div>
      )}
    </div>
  );
}
