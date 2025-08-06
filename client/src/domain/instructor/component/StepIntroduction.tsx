import { useState } from "react";
import { useFormContext } from "react-hook-form";

interface StepIntroductionProps {
  onNext: () => void;
}

const StepIntroduction = ({ onNext }: StepIntroductionProps) => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [simpleDescription, setSimpleDescription] = useState("");

  const { setValue, trigger } = useFormContext();

  const handleSubmit = () => {
    setValue("introduction", {
      name: name.trim(),
      description: description.trim(),
      simpleDescription: simpleDescription.trim(),
    });

    // 유효성 검사 후 다음 단계로 이동
    trigger("introduction").then((isValid) => {
      if (isValid) {
        onNext();
      }
    });
  };

  const isFormValid =
    name.trim() && description.trim() && simpleDescription.trim();

  return (
    <div className="border- flex w-[71rem] flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌를 소개해주세요</p>
      <div className="flex flex-col gap-6">
        <div className="flex flex-col gap-2">
          <label className="font-medium">강좌명</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="강좌 제목을 입력해주세요"
            className="rounded-lg border px-4 py-3"
            maxLength={50}
          />
          <span className="text-sm text-gray-500">{name.length}/50</span>
        </div>

        <div className="flex flex-col gap-2">
          <label className="font-medium">간단한 설명</label>
          <input
            type="text"
            value={simpleDescription}
            onChange={(e) => setSimpleDescription(e.target.value)}
            placeholder="강좌를 한 줄로 설명해주세요"
            className="rounded-lg border px-4 py-3"
            maxLength={100}
          />
          <span className="text-sm text-gray-500">
            {simpleDescription.length}/100
          </span>
        </div>

        <div className="flex flex-col gap-2">
          <label className="font-medium">상세 설명</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="강좌에 대한 자세한 설명을 작성해주세요"
            className="resize-vertical min-h-32 rounded-lg border px-4 py-3"
            maxLength={500}
          />
          <span className="text-sm text-gray-500">
            {description.length}/500
          </span>
        </div>

        <button
          onClick={handleSubmit}
          disabled={!isFormValid}
          className="mt-4 rounded-lg bg-blue-500 px-6 py-3 text-white disabled:bg-gray-300"
        >
          next
        </button>
      </div>
    </div>
  );
};

export default StepIntroduction;
