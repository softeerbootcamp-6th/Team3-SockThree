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

import { useForm, FormProvider } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  funnelSchema,
  type FunnelFormSchema,
} from "@/domain/instructor/schema/funnelSchema.ts";
import { useFunnel } from "@/domain/instructor/hook/useFunnel.ts";

import { FunnelSideBar } from "@/domain/instructor/component/FunnelSideBar.tsx";
import { useFunnelScroll } from "@/domain/instructor/hook/useFunnellScroll.ts";

// 타입 정의
type Context = {
  category?: string;
  subCategory?: string;
  level?: string;
  duration?: {
    startDate?: string;
    endDate?: string;
  };
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

const steps = [
  { key: "category", label: "대분류" },
  { key: "subCategory", label: "소분류" },
  { key: "level", label: "난이도" },
  { key: "duration", label: "강의기간" },
  { key: "maxHeadCount", label: "최대 인원" },
  { key: "uploadTimes", label: "업로드 일정" },
  { key: "price", label: "수강료" },
  { key: "introduction", label: "강좌 소개" },
  { key: "curriculum", label: "커리큘럼" },
  { key: "imageUrl", label: "사진" },
] as const;

// 상위 컴포넌트
const FunnelForm = () => {
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

  const methods = useForm<FunnelFormSchema>({
    resolver: zodResolver(funnelSchema),
    mode: "onChange",
  });

  const { goNextStep, currentIndex } = useFunnel<Context>(
    steps.map((s) => s.key)
  );

  const { containerRef, stepRef } = useFunnelScroll({
    stepIndex: currentIndex,
  });

  return (
    <FormProvider {...methods}>
      <div className="flex justify-center gap-7">
        <div
          ref={containerRef}
          className="scroll-snap-y h-screen snap-mandatory overflow-y-auto scroll-smooth pt-[160px] pb-[300px]"
        >
          {steps.map((s, i) => {
            if (i > currentIndex) return null;
            const StepComponent = stepComponentMap[s.key];
            return (
              <div
                id={`step-${s.key}`}
                ref={i === currentIndex ? stepRef : undefined}
                className="scroll-snap-start my-20 scroll-mt-[150px]"
              >
                <StepComponent key={s.key} onNext={goNextStep} />
              </div>
            );
          })}
        </div>

        <FunnelSideBar<Context> steps={steps} currentIndex={currentIndex} />
      </div>
    </FormProvider>
  );
};

export default FunnelForm;
