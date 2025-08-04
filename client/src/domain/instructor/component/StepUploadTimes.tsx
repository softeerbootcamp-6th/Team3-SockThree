import { useState } from "react";

interface StepUploadTimesProps {
  onNextStep: (context: { uploadTimes: string[] }) => void;
}

const StepUploadTimes = ({ onNextStep }: StepUploadTimesProps) => {
  const [uploadTimes, setUploadTimes] = useState<string[]>([]);
  const [newDateTime, setNewDateTime] = useState("");

  const addDateTime = () => {
    if (newDateTime && !uploadTimes.includes(newDateTime)) {
      const updatedTimes = [...uploadTimes, newDateTime];
      setUploadTimes(updatedTimes);
      setNewDateTime("");
    }
  };

  const removeDateTime = (timeToRemove: string) => {
    const updatedTimes = uploadTimes.filter((time) => time !== timeToRemove);
    setUploadTimes(updatedTimes);
  };

  const handleSubmit = () => {
    if (uploadTimes.length > 0) {
      onNextStep({ uploadTimes });
    }
  };

  return (
    <div className="border- flex w-[71rem] flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌 업로드 일정을 설정해주세요</p>
      <div className="flex flex-col gap-6">
        <div className="flex items-center gap-4">
          <input
            type="datetime-local"
            value={newDateTime}
            onChange={(e) => setNewDateTime(e.target.value)}
            className="rounded-lg border px-4 py-2"
          />
          <button
            onClick={addDateTime}
            disabled={!newDateTime}
            className="rounded-lg bg-blue-500 px-4 py-2 text-white disabled:bg-gray-300"
          >
            추가
          </button>
        </div>

        {uploadTimes.length > 0 && (
          <div className="flex flex-col gap-2">
            <p className="font-medium">선택된 업로드 일정:</p>
            <div className="flex flex-wrap gap-2">
              {uploadTimes.map((time, index) => (
                <div
                  key={index}
                  className="flex items-center gap-2 rounded-lg bg-gray-100 px-3 py-2"
                >
                  <span>{new Date(time).toLocaleString()}</span>
                  <button
                    onClick={() => removeDateTime(time)}
                    className="text-red-500 hover:text-red-700"
                  >
                    ✕
                  </button>
                </div>
              ))}
            </div>
          </div>
        )}

        <button
          onClick={handleSubmit}
          disabled={uploadTimes.length === 0}
          className="mt-4 rounded-lg bg-blue-500 px-6 py-3 text-white disabled:bg-gray-300"
        >
          next
        </button>
      </div>
    </div>
  );
};

export default StepUploadTimes;
