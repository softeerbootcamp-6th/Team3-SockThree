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
import Button from "@/shared/component/Button.tsx";

import CheckIcon from "@/assets/icons/default/check.svg?react";

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

// 상위 컴포넌트
const FunnelForm = () => {
  const steps = [
    { key: "category", label: "카테고리 선택" },
    { key: "subCategory", label: "세부 카테고리 선택" },
    { key: "level", label: "난이도 선택" },
    { key: "duration", label: "강의 기간 설정" },
    { key: "maxHeadCount", label: "최대 인원 설정" },
    { key: "uploadTimes", label: "업로드 일정 선택" },
    { key: "price", label: "가격 설정" },
    { key: "introduction", label: "소개 작성" },
    { key: "curriculum", label: "커리큘럼 작성" },
    { key: "imageUrl", label: "썸네일 업로드" },
  ] as const;

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

  const { goNextStep } = useFunnel<Context>(steps.map((s) => s.key));

  return (
    <FormProvider {...methods}>
      <div className="flex justify-center gap-7">
        <div className="flex flex-col items-center justify-center gap-[30px]">
          {steps.map((s) => {
            const StepComponent = stepComponentMap[s.key]; // 아래 참고

            return (
              <div id={`step-${s.key}`}>
                <StepComponent key={s.key} onNext={goNextStep} />
              </div>
            );
          })}
        </div>
        <aside className="sticky top-0 flex h-[50rem] w-[19rem] flex-col justify-between rounded-[var(--radius-20)] bg-white p-[1.7rem]">
          <ul className="space-y-2">
            {steps.map((s) => (
              <li key={s.key}>
                <a
                  href={`#step-${s.key}`}
                  className="flex w-[16.125rem] items-center justify-between gap-2 py-0.5"
                >
                  <span className="flex items-center gap-1">
                    <CheckIcon />
                    <span className="typo-label-0">{s.label}</span>
                  </span>
                  <span className="typo-title-5">curState</span>
                </a>
              </li>
            ))}
          </ul>
          <Button variant="default" size="md">
            강좌 생성하기
          </Button>
        </aside>
      </div>
    </FormProvider>
  );
};

export default FunnelForm;
