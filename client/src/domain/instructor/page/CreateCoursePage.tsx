import { useState } from "react";
import StepCategory from "@/domain/instructor/component/StepCategory.tsx";
import StepSubCategory from "@/domain/instructor/component/StepSubCategory.tsx";
import StepLevel from "@/domain/instructor/component/StepLevel.tsx";
import StepDateRange from "@/domain/instructor/component/StepDateRange.tsx";
import StepMaxHeadCount from "@/domain/instructor/component/StepMaxHeadCount.tsx";
import StepUploadTimes from "@/domain/instructor/component/StepUploadTimes.tsx";
import StepPrice from "@/domain/instructor/component/StepPrice.tsx";
import StepIntroduction from "@/domain/instructor/component/StepIntroduction.tsx";
import StepCurriculum from "@/domain/instructor/component/StepCurriculum.tsx";
import StepImageUpload from "@/domain/instructor/component/StepImageUpload.tsx";

// 타입 정의
type Context = {
  category?: string;
  subCategory?: string;
  level?: string;
  startDate?: string;
  endDate?: string;
  maxHeadCount?: number;
  uploadTimes?: string[]; // 강좌 업로드 날짜, 시간
  price?: number;
  introduction?: {
    name: string;
    description: string;
    simpleDescription: string;
  };
  curriculum?: string;
  imageUrl?: string;
};

// 상위 컴포넌트
const FunnelForm = () => {
  const [step, setStep] = useState(1);
  const [context, setContext] = useState<Context>({});

  const onNextStep = <K extends keyof Context>(
    key: K,
    value: Context[K],
    nextStep: number,
    shouldReset?: (prev: Context[K] | undefined, next: Context[K]) => boolean,
    resetKeys: Array<keyof Context> = []
  ) => {
    setContext((prev) => {
      const needsReset = shouldReset?.(prev[key], value);
      const reset = needsReset
        ? Object.fromEntries(resetKeys.map((k) => [k, undefined]))
        : {};
      return { ...prev, ...reset, [key]: value };
    });
    if (step < nextStep) setStep(nextStep);
  };

  return (
    <div className="flex flex-col items-center justify-center gap-[30px]">
      {step >= 1 && (
        <StepCategory
          onNextStep={({ category }) =>
            onNextStep("category", category, 2, (prev, next) => prev !== next, [
              "subCategory",
              "level",
              "startDate",
              "endDate",
              "maxHeadCount",
              "uploadTimes",
              "price",
              "introduction",
              "curriculum",
              "imageUrl",
            ])
          }
        />
      )}
      {step >= 2 && context.category && (
        <StepSubCategory
          category={context.category}
          onNextStep={({ subCategory }) =>
            onNextStep("subCategory", subCategory, 3)
          }
        />
      )}
      {step >= 3 && context.subCategory && (
        <StepLevel
          subCategory={context.subCategory}
          onNextStep={({ level }) => onNextStep("level", level, 4)}
        />
      )}
      {step >= 4 && context.level && (
        <StepDateRange
          onNextStep={({ startDate, endDate }) => {
            setContext((prev) => ({
              ...prev,
              startDate,
              endDate,
            }));
            setStep(5);
          }}
        />
      )}
      {step >= 5 && context.startDate && context.endDate && (
        <StepMaxHeadCount
          onNextStep={({ maxHeadCount }) =>
            onNextStep("maxHeadCount", maxHeadCount, 6)
          }
        />
      )}
      {step >= 6 && context.maxHeadCount && (
        <StepUploadTimes
          onNextStep={({ uploadTimes }) =>
            onNextStep("uploadTimes", uploadTimes, 7)
          }
        />
      )}
      {step >= 7 && context.uploadTimes && (
        <StepPrice onNextStep={({ price }) => onNextStep("price", price, 8)} />
      )}
      {step >= 8 && context.price !== undefined && (
        <StepIntroduction
          onNextStep={({ introduction }) =>
            onNextStep("introduction", introduction, 9)
          }
        />
      )}
      {step >= 9 && context.introduction && (
        <StepCurriculum
          onNextStep={({ curriculum }) =>
            onNextStep("curriculum", curriculum, 10)
          }
        />
      )}
      {step >= 10 && context.curriculum && (
        <StepImageUpload
          onNextStep={({ imageUrl }) => onNextStep("imageUrl", imageUrl, 11)}
        />
      )}
    </div>
  );
};

export default FunnelForm;
