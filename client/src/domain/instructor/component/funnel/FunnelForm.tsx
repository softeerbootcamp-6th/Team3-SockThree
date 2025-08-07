import StepCategory from "@/domain/instructor/component/Step/StepCategory.tsx";
import StepSubCategory from "@/domain/instructor/component/Step/StepSubCategory.tsx";
import StepLevel from "@/domain/instructor/component/Step/StepLevel.tsx";
import StepDateRange from "@/domain/instructor/component/Step/StepDateRange.tsx";
import StepMaxHeadCount from "@/domain/instructor/component/Step/StepMaxHeadCount.tsx";
import StepUploadTimes from "@/domain/instructor/component/Step/StepUploadTimes.tsx";
import StepPrice from "@/domain/instructor/component/Step/StepPrice.tsx";
import StepIntroduction from "@/domain/instructor/component/Step/StepIntroduction.tsx";
import StepCurriculum from "@/domain/instructor/component/Step/StepCurriculum.tsx";
import StepImageUpload from "@/domain/instructor/component/Step/StepImageUpload.tsx";

import { useFunnelScroll } from "@/domain/instructor/hook/useFunnellScroll.ts";
import * as React from "react";

interface FunnelFormProps<T> {
  steps: readonly {
    key: keyof T;
    label: string;
  }[];
  currentStepIndex: number;
  goNextStep: (key: keyof T) => void;
}

// 상위 컴포넌트
export function FunnelForm<T>({
  steps,
  currentStepIndex,
  goNextStep,
}: FunnelFormProps<T>) {
  // 타입 명시 제거하고 추론하도록 변경
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

  const { containerRef, stepRef } = useFunnelScroll({
    stepIndex: currentStepIndex,
  });

  return (
    <div className="flex justify-center">
      <div
        ref={containerRef}
        className="scroll-snap-y h-screen snap-mandatory overflow-y-auto scroll-smooth pb-[300px]"
      >
        {steps.map((s, i) => {
          if (i > currentStepIndex) return null;
          const stepKey = String(s.key) as keyof typeof stepComponentMap;
          const StepComponent = stepComponentMap[stepKey];

          // StepComponent가 존재하는지 체크
          if (!StepComponent) {
            console.warn(`Step component not found for key: ${stepKey}`);
            return null;
          }

          return (
            <div
              key={String(s.key)}
              id={`step-${String(s.key)}`}
              ref={
                i === currentStepIndex
                  ? (stepRef as React.RefObject<HTMLDivElement>)
                  : undefined
              }
              className="scroll-snap-start mb-5 scroll-mb-[150px]"
            >
              <StepComponent onNext={() => goNextStep(s.key)} />
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default FunnelForm;
