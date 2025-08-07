import FunnelForm from "@/domain/instructor/component/funnel/FunnelForm.tsx";
import { FormProvider, useForm } from "react-hook-form";
import {
  type FunnelFormSchema,
  funnelSchema,
} from "@/domain/instructor/schema/funnelSchema.ts";
import { zodResolver } from "@hookform/resolvers/zod";
import { useFunnel } from "@/domain/instructor/hook/useFunnel.ts";
import { FunnelSideBar } from "@/domain/instructor/component/funnel/FunnelSideBar.tsx";
import { useFunnelScroll } from "@/domain/instructor/hook/useFunnellScroll.ts";
import FunnelHeader from "@/domain/instructor/component/funnel/FunnelHeader.tsx";

type Context = {
  category?: string;
  subCategory?: string;
  level?: string;
  duration?: {
    startDate?: string;
    endDate?: string;
  };
  maxHeadCount?: number;
  uploadTimes?: string[];
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

const CreateCoursePage = () => {
  const methods = useForm<FunnelFormSchema>({
    resolver: zodResolver(funnelSchema),
    mode: "onChange",
  });

  const { step, goNextStep, currentIndex } = useFunnel<Context>(
    steps.map((s) => s.key)
  );

  const { containerRef, stepRef } = useFunnelScroll({
    stepIndex: currentIndex,
  });

  console.log(currentIndex, steps.length);

  return (
    <div className="flex min-h-screen flex-col pr-[5rem] pl-[10rem]">
      <FormProvider {...methods}>
        <FunnelHeader currentIndex={currentIndex} totalSteps={steps.length} />
        <main className="flex flex-row gap-7">
          <FunnelForm<Context>
            steps={steps}
            currentStepIndex={currentIndex}
            goNextStep={goNextStep}
            containerRef={containerRef}
            stepRef={stepRef}
          />
          <FunnelSideBar<Context> steps={steps} currentStep={step} />
        </main>
      </FormProvider>
    </div>
  );
};

export default CreateCoursePage;
