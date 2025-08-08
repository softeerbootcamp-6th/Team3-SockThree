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

import * as React from "react";
import type { FunnelContext } from "@/domain/instructor/types/funnel";
import type { StepKey } from "@/domain/instructor/hook/useFunnelState.ts";
import { useFunnelScroll } from "@/domain/instructor/hook/useFunnellScroll.ts";

interface FunnelFormProps {
  context: FunnelContext;
  curStep: number;
  stepKeys: readonly StepKey[];
  handleValidChange: <K extends StepKey>(
    key: K,
    value: FunnelContext[K]
  ) => void;
}

export const FunnelForm = ({
  context,
  curStep,
  stepKeys,
  handleValidChange,
}: FunnelFormProps) => {
  const { containerRef, stepRef } = useFunnelScroll({
    stepIndex: curStep,
  });
  return (
    <div
      ref={containerRef}
      className="scroll-snap-y h-screen snap-mandatory overflow-y-auto scroll-smooth pb-[300px]"
    >
      {stepKeys.map((stepKey, i) => {
        if (i > curStep) return null;

        return (
          <div
            id={`step-${String(stepKey)}`}
            ref={i === curStep ? stepRef : undefined}
            key={stepKey}
            className="scroll-snap-start mb-5 max-w-[65rem] scroll-mb-[150px]"
          >
            <StepRenderer
              key={stepKey}
              stepKey={stepKey}
              stepIndex={i}
              value={context[stepKey]}
              onValidSubmit={(val) => handleValidChange(stepKey, val)}
              context={context}
            />
          </div>
        );
      })}
    </div>
  );
};

export default FunnelForm;

type StepProps<K extends StepKey> = {
  value?: FunnelContext[K];
  onValidSubmit: (value: FunnelContext[K]) => void;
};

interface SubCategoryProps extends StepProps<"subCategory"> {
  category: string; // 반드시 받아야 하는 추가 prop
}

const stepComponentMap: {
  // 기본: 대부분 StepProps<K>
  [K in Exclude<StepKey, "subCategory">]: React.FC<StepProps<K>>;
} & {
  subCategory: React.FC<SubCategoryProps>; // subCategory만 확장 타입
} = {
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

interface StepRendererProps<K extends StepKey = StepKey> {
  stepKey: K;
  stepIndex: number;
  value?: FunnelContext[K];
  onValidSubmit: (value: FunnelContext[K]) => void;
  ref?: React.RefObject<HTMLElement | null> | null;
  category?: K extends "subCategory" ? string : never;
  context: FunnelContext;
}

const StepRenderer = <K extends StepKey>({
  stepKey,
  value,
  onValidSubmit,
  context,
}: StepRendererProps<K>) => {
  const StepComponent = stepComponentMap[stepKey] as React.FC<StepProps<K>>;

  if (stepKey === "subCategory") {
    return (
      <StepComponent
        value={value}
        onValidSubmit={onValidSubmit}
        {...(stepKey === "subCategory" ? { category: context.category } : {})}
      />
    );
  }

  return <StepComponent value={value} onValidSubmit={onValidSubmit} />;
};
