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
  const steps: Array<keyof Context> = [
    "category",
    "subCategory",
    "level",
    "startDate",
    // "endDate",
    "maxHeadCount",
    "uploadTimes",
    "price",
    "introduction",
    "curriculum",
    "imageUrl",
  ];

  const methods = useForm<FunnelFormSchema>({
    resolver: zodResolver(funnelSchema),
    mode: "onChange",
  });

  const { goNextStep, step } = useFunnel<Context>(steps);

  return (
    <FormProvider {...methods}>
      <div className="flex flex-col items-center justify-center gap-[30px]">
        {step === "category" && <StepCategory onNext={goNextStep} />}
        {step === "subCategory" && <StepSubCategory onNext={goNextStep} />}
        {step === "level" && <StepLevel onNext={goNextStep} />}
        {step === "startDate" && <StepDateRange onNext={goNextStep} />}
        {step === "maxHeadCount" && <StepMaxHeadCount onNext={goNextStep} />}
        {step === "uploadTimes" && <StepUploadTimes onNext={goNextStep} />}
        {step === "price" && <StepPrice onNext={goNextStep} />}
        {step === "introduction" && <StepIntroduction onNext={goNextStep} />}
        {step === "curriculum" && <StepCurriculum onNext={goNextStep} />}
        {step === "imageUrl" && <StepImageUpload onNext={goNextStep} />}
      </div>
    </FormProvider>
  );
};

export default FunnelForm;
