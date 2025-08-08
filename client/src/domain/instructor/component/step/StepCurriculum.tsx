import { useState } from "react";

interface StepCurriculumProps {
  value?: string;
  onValidSubmit: (curriculum: string) => void;
}

const StepCurriculum = ({ value, onValidSubmit }: StepCurriculumProps) => {
  const [curriculum, setCurriculum] = useState(value ?? "");
  const [error, setError] = useState<string | null>(null);

  const handleBlur = () => {
    const trimmed = curriculum.trim();

    if (trimmed.length === 0) {
      setError("커리큘럼을 작성해주세요");
      return;
    }

    setError(null);
    onValidSubmit(trimmed);
  };

  return (
    <div className="flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌 커리큘럼을 작성해주세요</p>

      <div className="flex flex-col gap-6">
        <div className="flex flex-col gap-2">
          <label className="font-medium">커리큘럼</label>
          <textarea
            value={curriculum}
            onChange={(e) => setCurriculum(e.target.value)}
            onBlur={handleBlur}
            placeholder={`강좌의 커리큘럼을 상세히 작성해주세요\n예:\n1주차: 기초 이론 학습\n2주차: 실습 과정\n3주차: 프로젝트 진행`}
            className="resize-vertical min-h-40 rounded-lg border px-4 py-3"
            maxLength={1000}
          />
          <span className="text-sm text-gray-500">
            {curriculum.length}/1000
          </span>
        </div>

        {error && <p className="text-sm text-red-500">{error}</p>}
      </div>
    </div>
  );
};

export default StepCurriculum;
