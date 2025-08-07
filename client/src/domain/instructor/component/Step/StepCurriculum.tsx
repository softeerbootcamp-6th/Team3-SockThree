import { useState } from "react";
import { useFormContext } from "react-hook-form";

interface StepCurriculumProps {
  onNext: () => void;
}

const StepCurriculum = ({ onNext }: StepCurriculumProps) => {
  const [curriculum, setCurriculum] = useState("");

  const { setValue, trigger } = useFormContext();

  const handleSubmit = () => {
    setValue("curriculum", curriculum.trim());

    // 유효성 검사 후 다음 단계로 이동
    trigger("curriculum").then((isValid) => {
      if (isValid) {
        onNext();
      }
    });
  };

  return (
    <div className="border- flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌 커리큘럼을 작성해주세요</p>
      <div className="flex flex-col gap-6">
        <div className="flex flex-col gap-2">
          <label className="font-medium">커리큘럼</label>
          <textarea
            value={curriculum}
            onChange={(e) => setCurriculum(e.target.value)}
            placeholder="강좌의 커리큘럼을 상세히 작성해주세요&#10;예:&#10;1주차: 기초 이론 학습&#10;2주차: 실습 과정&#10;3주차: 프로젝트 진행"
            className="resize-vertical min-h-40 rounded-lg border px-4 py-3"
            maxLength={1000}
          />
          <span className="text-sm text-gray-500">
            {curriculum.length}/1000
          </span>
        </div>

        <button
          onClick={handleSubmit}
          disabled={!curriculum.trim()}
          className="mt-4 rounded-lg bg-blue-500 px-6 py-3 text-white disabled:bg-gray-300"
        >
          다음 단계로
        </button>
      </div>
    </div>
  );
};

export default StepCurriculum;
