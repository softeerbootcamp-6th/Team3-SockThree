import FunnelForm from "@/domain/instructor/page/FunnelForm.tsx";
import { FormProvider, useForm } from "react-hook-form";
import {
  type FunnelFormSchema,
  funnelSchema,
} from "@/domain/instructor/schema/funnelSchema.ts";
import { zodResolver } from "@hookform/resolvers/zod";
import { useFunnel } from "@/domain/instructor/hook/useFunnel.ts";
import { FunnelSideBar } from "@/domain/instructor/component/FunnelSideBar.tsx";
import { useFunnelScroll } from "@/domain/instructor/hook/useFunnellScroll.ts";

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

  return (
    <div className="flex min-h-screen flex-col pr-[5rem] pl-[10rem]">
      <FormProvider {...methods}>
        <header className="sticky top-0 z-20 border-gray-200 bg-bg px-10 py-6">
          <h1 className="text-2xl font-semibold">강좌 만들기</h1>
          <p className="mt-1 text-sm text-blue-500">
            모임 개설까지 거의 다 왔어요
          </p>
          <div className="mt-4 h-2 overflow-hidden rounded-full bg-gray-100">
            <div
              className="h-full bg-blue-500 transition-all"
              style={{ width: `${(currentIndex / steps.length) * 100}%` }}
            />
          </div>
        </header>
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
