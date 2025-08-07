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

interface FunnelFormProps<T> {
  steps: Array<{
    key: keyof T;
    label: string;
  }>;
  currentStepIndex: number;
  goNextStep: (key: keyof T) => void;
}

// 상위 컴포넌트
export function FunnelForm<T>({
  steps,
  currentStepIndex,
  goNextStep,
}: FunnelFormProps<T>) {
  const stepComponentMap: Record<
    (typeof steps)[number]["key"],
    (props: { onNext: () => void }) => JSX.Element
  > = {
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
  };

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
          const StepComponent = stepComponentMap[s.key];
          return (
            <div
              key={s.key}
              id={`step-${s.key}`}
              ref={i === currentStepIndex ? stepRef : undefined}
              className="scroll-snap-start mb-5 scroll-mb-[150px]"
            >
              <StepComponent onNext={goNextStep} />
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default FunnelForm;
