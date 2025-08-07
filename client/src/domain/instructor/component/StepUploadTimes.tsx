import { useEffect, useState } from "react";
import { XIcon } from "lucide-react";
import Button from "@/shared/component/Button";
import Chips from "@/shared/components/Chips";
import Select from "@/shared/component/Select";

const formatHourToKoreanTime = (hour: number): string => {
  if (hour === 0) return "오전 12시";
  if (hour < 12) return `오전 ${hour}시`;
  if (hour === 12) return "오후 12시";
  return `오후 ${hour - 12}시`;
};

const DAYS = ["월", "화", "수", "목", "금", "토", "일"];

// 시간 옵션 생성
const TIME_OPTIONS = Array.from({ length: 24 }, (_, i) => ({
  value: i.toString(),
  label: formatHourToKoreanTime(i),
}));

interface StepUploadTimesProps {
  onNext: () => void;
}

export default function StepUploadTimes({ onNext }: StepUploadTimesProps) {
  const [selectedDay, setSelectedDay] = useState<string | null>(null);
  const [uploadTimes, setUploadTimes] = useState<string[]>([]);
  const [selectedHour, setSelectedHour] = useState<string>("20");

  const handleAddTime = () => {
    if (!selectedDay || !selectedHour) return;
    const timeString = formatHourToKoreanTime(parseInt(selectedHour));
    const newEntry = `${selectedDay}요일 ${timeString}`;
    if (!uploadTimes.includes(newEntry)) {
      setUploadTimes([...uploadTimes, newEntry]);
    }
  };
  const handleRemoveTime = (entry: string) => {
    setUploadTimes(uploadTimes.filter((t) => t !== entry));
  };

  return (
    <div className="border- flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
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
    </div>
  );
}
