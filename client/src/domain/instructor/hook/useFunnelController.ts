import { useCallback } from "react";
import type { FunnelContext } from "@/domain/instructor/types/funnel.d.ts";

export type StepKey = keyof FunnelContext;

type ValidatorMap = {
  [K in StepKey]: (value: FunnelContext[K]) => boolean;
};

interface UseFunnelControllerProps {
  curStep: number;
  setCurStep: (step: number) => void;
  onValidChange: <K extends StepKey>(key: K, value: FunnelContext[K]) => void;
  stepKeys: readonly StepKey[];
  onError?: (key: StepKey) => void;
}

export const useFunnelController = (
  props: UseFunnelControllerProps & { validators: ValidatorMap }
) => {
  const { curStep, setCurStep, onValidChange, stepKeys, validators, onError } =
    props;
  const handleValidChange = useCallback(
    <K extends StepKey>(stepKey: K, value: FunnelContext[K]) => {
      const stepIdx = stepKeys.indexOf(stepKey);
      const isValid = validators[stepKey](value);

      if (!isValid) {
        onError?.(stepKey);
        return;
      }
      onValidChange(stepKey, value);
      if (curStep === stepIdx) {
        setCurStep(curStep + 1);
      }
      return;
    },
    [curStep, setCurStep, onValidChange, onError, stepKeys, validators]
  );
  return {
    handleValidChange,
  };
};
