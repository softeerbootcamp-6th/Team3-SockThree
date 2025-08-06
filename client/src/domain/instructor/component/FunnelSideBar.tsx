import { useFormContext, useWatch } from "react-hook-form";
import Button from "@/shared/component/Button.tsx";
import CheckIcon from "@/assets/icons/default/check.svg?react";

interface FunnelSideBarProps<T> {
  steps: Array<{
    key: keyof T;
    label: string;
  }>;
  currentIndex: number;
  goToStep: (key: keyof T) => void;
}

export function FunnelSideBar<T>({
  steps,
  currentIndex,
}: FunnelSideBarProps<T>) {
  const { control, formState } = useFormContext();
  const values = useWatch({ control });

  // ✅ 외부에서 가공해둠 (map 이전)
  const stepRenderData = steps.map((step, index) => {
    const value = values?.[step.key];
    const error = formState.errors?.[step.key];
    const isRendered = index < currentIndex;
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
    <aside className="sticky top-0 flex h-[50rem] w-[19rem] flex-col justify-between rounded-[var(--radius-20)] bg-white p-[1.7rem]">
      <ul className="space-y-2">
        {stepRenderData.map((step) => (
          <li key={step.key as string}>
            <a
              href={step.isDisabled ? undefined : `#step-${step.key}`}
              className={`... ${step.isDisabled ? "cursor-not-allowed" : ""}`}
            >
              <div className="flex items-center justify-between">
                <span className="flex items-center gap-2">
                  <CheckIcon className={step.iconColor} />
                  <span>{step.label}</span>
                </span>
                <span className="typo-body-4">{step.curState}</span>
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
