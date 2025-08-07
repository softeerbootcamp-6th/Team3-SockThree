import { useFormContext, useWatch } from "react-hook-form";
import Button from "@/shared/components/Button.tsx";
import CheckIcon from "@/assets/icons/default/check.svg?react";
import type { Context } from "@/domain/instructor/page/CreateCoursePage.tsx";

interface FunnelSideBarProps<T> {
  steps: readonly {
    key: keyof T;
    label: string;
  }[];
  currentStepIndex: number;
}

export function FunnelSideBar<T>({
  steps,
  currentStepIndex,
}: FunnelSideBarProps<T>) {
  const { control, formState } = useFormContext<Context>();
  const values = useWatch<Context>({ control });

  const stepRenderData = steps.map((step, index) => {
    const value = values?.[step.key as keyof Context];
    const error = formState.errors?.[step.key as keyof Context];
    const isRendered = index < currentStepIndex && value !== undefined;
    const isError = isRendered && !!error;

    return {
      ...step,
      curState: value,
      isDisabled: !isRendered,
      iconColor: !isRendered
        ? "text-gray-500"
        : isError
          ? "text-red-500"
          : "text-main-500",
    };
  });

  return (
    <aside className="sticky top-[8.5rem] flex h-[50rem] w-[19rem] flex-col justify-between self-start rounded-[var(--radius-20)] bg-white p-[1.7rem]">
      <ul className="space-y-2">
        {stepRenderData.map((step) => (
          <li key={step.key as string}>
            <a
              href={step.isDisabled ? undefined : `#step-${String(step.key)}`}
              className={`... ${step.isDisabled ? "cursor-not-allowed" : ""}`}
            >
              <div className="flex items-center justify-between">
                <span className="flex items-center gap-2">
                  <CheckIcon className={step.iconColor} />
                  <span>{step.label}</span>
                </span>
                <span className="text-gray-500">
                  {step.curState ? "완료" : ""}
                </span>
              </div>
            </a>
          </li>
        ))}
      </ul>
      <Button variant="default" size="md">
        강좌 생성하기
      </Button>
    </aside>
  );
}
