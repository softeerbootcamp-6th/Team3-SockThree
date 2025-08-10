import CheckIcon from "@/assets/icons/default/check.svg?react";
// import { cn } from '@/lib/utils'; // 없으면 className 직접 조건부 처리
import Button from "@/shared/components/Button";
import type { StepKey } from "@/domain/instructor/hook/useFunnelController";
import type { FunnelContext } from "@/domain/instructor/types/funnel";
import { useCallback } from "react";

interface FunnelSideBarProps {
  stepKeys: readonly StepKey[];
  curStep: number;
  funnelState: FunnelContext;
  isStepValid: (key: StepKey) => boolean;
}

export const FunnelSideBar = ({
  stepKeys,
  curStep,
  funnelState,
  isStepValid,
}: FunnelSideBarProps) => {
  const renderSummary = useCallback(
    (key: StepKey): string => {
      const val = funnelState[key];

      if (val == null) return "";

      switch (key) {
        case "duration": {
          const duration = val as FunnelContext["duration"];
          return `${duration.startDate.toLocaleDateString()}-${duration.endDate.toLocaleDateString()}`;
        }
        case "introduction": {
          return "완료";
        }
        case "uploadTimes": {
          const arr = val as string[];
          return `${arr.length}개 등록됨`;
        }
        case "price":
          return `${val as number}원`;
        case "maxHeadCount":
          return `${val as number}명`;
        case "curriculum":
          return "완료";
        case "category":
          return (val as string).replace(/_/g, " ");
        case "subCategory":
        case "level":
        case "imageUrl":
          return (val as string).replace(/_/g, " ");

        default:
          return "";
      }
    },
    [funnelState]
  );

  return (
    <aside className="sticky top-[8.5rem] flex h-[50rem] w-[19rem] flex-col justify-between self-start rounded-[var(--radius-20)] bg-white p-[1.7rem]">
      <ul className="space-y-2">
        {stepKeys.map((key, i) => {
          const isDisabled = i >= curStep;
          const summary = renderSummary(key);
          const isValid = isStepValid(key) ?? false;
          return (
            <li key={key}>
              <a
                href={isDisabled ? undefined : `#step-${String(key)}`}
                className={`... ${isDisabled ? "cursor-not-allowed" : ""}`}
              />
              <div className="flex items-center justify-between">
                <span className="flex items-center gap-2">
                  <CheckIcon />
                  <span>{stepLabelMap[key]}</span>
                </span>
                {!isDisabled && <span>{isValid ? summary : "수정 필요"}</span>}
              </div>
            </li>
          );
        })}
      </ul>
      <Button
        variant="default"
        size="md"
        disabled={stepKeys.length !== curStep}
      >
        강좌 생성하기
      </Button>
    </aside>
  );
};

const stepLabelMap: Record<StepKey, string> = {
  category: "카테고리",
  subCategory: "세부 카테고리",
  level: "난이도",
  duration: "기간",
  maxHeadCount: "모집 인원",
  uploadTimes: "업로드 시간",
  price: "수강료",
  introduction: "소개",
  curriculum: "커리큘럼",
  imageUrl: "썸네일",
};
