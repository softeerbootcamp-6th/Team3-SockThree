import StepCategory from "@/domain/instructor/component/step/StepCategory.tsx";
import StepSubCategory from "@/domain/instructor/component/step/StepSubCategory.tsx";
import StepLevel from "@/domain/instructor/component/step/StepLevel.tsx";
import StepDateRange from "@/domain/instructor/component/step/StepDateRange.tsx";
import StepMaxHeadCount from "@/domain/instructor/component/step/StepMaxHeadCount.tsx";
import StepUploadTimes from "@/domain/instructor/component/step/StepUploadTimes.tsx";
import StepPrice from "@/domain/instructor/component/step/StepPrice.tsx";
import StepIntroduction from "@/domain/instructor/component/step/StepIntroduction.tsx";
import StepCurriculum from "@/domain/instructor/component/step/StepCurriculum.tsx";
import StepImageUpload from "@/domain/instructor/component/step/StepImageUpload.tsx";

import { useFunnelScroll } from "@/domain/instructor/hook/useFunnellScroll.ts";
import * as React from "react";
import { useCallback } from "react";

interface FunnelFormProps<T> {
  steps: readonly {
    key: keyof T;
    label: string;
  }[];
  currentStepIndex: number;
  goNextStep: (key: keyof T) => void;
}

const stepComponentMap = {
  category: StepCategory,
  subCategory: StepSubCategory,
  level: StepLevel,
  duration: StepDateRange,
  maxHeadCount: StepMaxHeadCount,
  uploadTimes: StepUploadTimes,
  price: StepPrice,
  introduction: StepIntroduction,
  curriculum: StepCurriculum,
  imageUrl: StepImageUpload,
} as const; // as const로 정확한 타입 추론

// 상위 컴포넌트
export function FunnelForm<T>({
  steps,
  currentStepIndex,
  goNextStep,
}: FunnelFormProps<T>) {
  const { containerRef, stepRef } = useFunnelScroll({
    stepIndex: currentStepIndex,
  });

  const onNext = useCallback(
    (key: keyof T) => () => {
      goNextStep(key);
    },
    [goNextStep]
  );

  return (
    <div className="flex w-[71rem] justify-start">
      <div
        ref={containerRef}
        className="scroll-snap-y h-screen snap-mandatory overflow-y-auto scroll-smooth pb-[300px]"
      >
        {steps.map((s, i) => {
          if (i > currentStepIndex) return null;
          const stepKey = String(s.key) as keyof typeof stepComponentMap;
          const StepComponent = stepComponentMap[stepKey];
          if (!StepComponent) return null;

          return (
            <div
              key={String(s.key)}
              id={`step-${String(s.key)}`}
              ref={
                i === currentStepIndex
                  ? (stepRef as React.RefObject<HTMLDivElement>)
                  : undefined
              }
              className="scroll-snap-start mb-5 w-[71rem] scroll-mb-[150px]"
            >
              <StepComponent onNext={onNext(s.key)} />
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default FunnelForm;
