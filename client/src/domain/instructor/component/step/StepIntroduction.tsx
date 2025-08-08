import { useState } from "react";
import type { FunnelContext } from "@/domain/instructor/types/funnel";

interface StepIntroductionProps {
  value?: FunnelContext["introduction"];
  onValidSubmit: (val: FunnelContext["introduction"]) => void;
}

const StepIntroduction = ({ value, onValidSubmit }: StepIntroductionProps) => {
  const [name, setName] = useState(value?.name ?? "");
  const [simpleDescription, setSimpleDescription] = useState(
    value?.simpleDescription ?? ""
  );
  const [description, setDescription] = useState(value?.description ?? "");

  const [error, setError] = useState("");

  const handleBlur = () => {
    const trimmed = {
      name: name.trim(),
      simpleDescription: simpleDescription.trim(),
      description: description.trim(),
    };

    const isAllValid = Object.values(trimmed).every((v) => v.length > 0);
    if (!isAllValid) {
      setError("모든 항목을 입력해주세요");
      return;
    }

    setError("");
    onValidSubmit(trimmed);
  };

  return (
    <div className="flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌를 소개해주세요</p>

      <div className="flex flex-col gap-6">
        {/* 강좌명 */}
        <div className="flex flex-col gap-2">
          <label className="font-medium">강좌명</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            onBlur={handleBlur}
            placeholder="강좌 제목을 입력해주세요"
            className="rounded-lg border px-4 py-3"
            maxLength={50}
          />
          <span className="text-sm text-gray-500">{name.length}/50</span>
        </div>

        {/* 간단한 설명 */}
        <div className="flex flex-col gap-2">
          <label className="font-medium">간단한 설명</label>
          <input
            type="text"
            value={simpleDescription}
            onChange={(e) => setSimpleDescription(e.target.value)}
            onBlur={handleBlur}
            placeholder="강좌를 한 줄로 설명해주세요"
            className="rounded-lg border px-4 py-3"
            maxLength={100}
          />
          <span className="text-sm text-gray-500">
            {simpleDescription.length}/100
          </span>
        </div>

        {/* 상세 설명 */}
        <div className="flex flex-col gap-2">
          <label className="font-medium">상세 설명</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            onBlur={handleBlur}
            placeholder="강좌에 대한 자세한 설명을 작성해주세요"
            className="resize-vertical min-h-32 rounded-lg border px-4 py-3"
            maxLength={500}
          />
          <span className="text-sm text-gray-500">
            {description.length}/500
          </span>
        </div>

        {/* 에러 메시지 */}
        {error && <p className="text-sm text-red-500">{error}</p>}
      </div>
    </div>
  );
};

export default StepIntroduction;
