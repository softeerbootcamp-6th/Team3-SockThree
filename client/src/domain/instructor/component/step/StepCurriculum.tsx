import { useEffect, useRef, useState } from "react";
import { useFormContext } from "react-hook-form";

interface StepCurriculumProps {
  onNext: () => void;
}

const StepCurriculum = ({ onNext }: StepCurriculumProps) => {
  const [curriculum, setCurriculum] = useState("");

  const {
    setValue,
    trigger,
    register,
    formState: { errors },
  } = useFormContext();

  const debounceRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const updateAndValidate = () => {
    const trimmed = curriculum.trim();
    if (!trimmed) return;

    setValue("curriculum", trimmed, { shouldValidate: true });

    if (debounceRef.current) clearTimeout(debounceRef.current);
    debounceRef.current = setTimeout(async () => {
      const isValid = await trigger("curriculum");
      if (isValid) {
        onNext();
      }
    }, 300);
  };

  useEffect(() => {
    updateAndValidate();
  }, [curriculum]);

  useEffect(() => {
    register("curriculum", {
      required: "커리큘럼을 작성해주세요",
    });
  }, [register]);

  return (
    <div className="flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌 커리큘럼을 작성해주세요</p>

      <div className="flex flex-col gap-6">
        <div className="flex flex-col gap-2">
          <label className="font-medium">커리큘럼</label>
          <textarea
            value={curriculum}
            onChange={(e) => setCurriculum(e.target.value)}
            placeholder={`강좌의 커리큘럼을 상세히 작성해주세요\n예:\n1주차: 기초 이론 학습\n2주차: 실습 과정\n3주차: 프로젝트 진행`}
            className="resize-vertical min-h-40 rounded-lg border px-4 py-3"
            maxLength={1000}
          />
          <span className="text-sm text-gray-500">
            {curriculum.length}/1000
          </span>
        </div>

        {errors.curriculum && (
          <p className="text-sm text-red-500">
            {errors.curriculum.message as string}
          </p>
        )}
      </div>
    </div>
  );
};

export default StepCurriculum;
