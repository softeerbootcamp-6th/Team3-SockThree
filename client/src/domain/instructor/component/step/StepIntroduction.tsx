import { useEffect, useRef, useState } from "react";
import { useFormContext } from "react-hook-form";

interface StepIntroductionProps {
  onNext: () => void;
}

const StepIntroduction = ({ onNext }: StepIntroductionProps) => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [simpleDescription, setSimpleDescription] = useState("");

  const {
    setValue,
    trigger,
    register,
    formState: { errors },
  } = useFormContext();

  const debounceRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const updateAndValidate = () => {
    const trimmed = {
      name: name.trim(),
      description: description.trim(),
      simpleDescription: simpleDescription.trim(),
    };

    const isAllFilled = Object.values(trimmed).every((v) => v.length > 0);
    if (!isAllFilled) return;

    setValue("introduction", trimmed, { shouldValidate: true });

    if (debounceRef.current) clearTimeout(debounceRef.current);
    debounceRef.current = setTimeout(async () => {
      const isValid = await trigger("introduction");
      if (isValid) {
        onNext();
      }
    }, 300);
  };

  useEffect(() => {
    updateAndValidate();
  }, [name, description, simpleDescription]);

  useEffect(() => {
    register("introduction", {
      validate: (value) => {
        if (
          !value?.name?.trim() ||
          !value?.simpleDescription?.trim() ||
          !value?.description?.trim()
        ) {
          return "모든 항목을 입력해주세요";
        }
        return true;
      },
    });
  }, [register]);

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
            placeholder="강좌에 대한 자세한 설명을 작성해주세요"
            className="resize-vertical min-h-32 rounded-lg border px-4 py-3"
            maxLength={500}
          />
          <span className="text-sm text-gray-500">
            {description.length}/500
          </span>
        </div>

        {/* 에러 메시지 */}
        {errors.introduction && (
          <p className="text-sm text-red-500">
            {errors.introduction.message as string}
          </p>
        )}
      </div>
    </div>
  );
};

export default StepIntroduction;
