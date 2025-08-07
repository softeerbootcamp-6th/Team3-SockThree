import { useState, useCallback } from "react";
import type { FunnelContext } from "@/domain/instructor/types/funnel.d.ts";
import { useFunnelController } from "@/domain/instructor/hook/useFunnelController.ts";

export type StepKey = keyof FunnelContext;

export const useFunnelState = () => {
  const [funnelState, setFunnelState] = useState<FunnelContext>(
    {} as FunnelContext
  );
  const [curStep, setCurStep] = useState(0);
  const stepKeys = [
    "category",
    "subCategory",
    "level",
    "duration",
    "maxHeadCount",
    "uploadTimes",
    "price",
    "introduction",
    "curriculum",
    "imageUrl",
  ] as const;

  const validators: {
    [K in StepKey]: (value: FunnelContext[K]) => boolean;
  } = {
    category: (value) => !!value,
    subCategory: (value) => !!value,
    level: (value) => !!value,
    duration: (value) => !!value?.startDate && !!value?.endDate,
    maxHeadCount: (value) => value > 0,
    uploadTimes: (value) => value?.length > 0,
    price: (value) => value >= 0,
    introduction: (value) => !!value?.name && !!value?.description && !!value?.simpleDescription,
    curriculum: (value) => !!value,
    imageUrl: (value) => !!value,
  };

  const isStepValid = useCallback(
    <K extends StepKey>(key: K): boolean => {
      const validator = validators[key];
      const value = funnelState[key];
      return validator(value);
    },
    [funnelState]
  );


  const onValidChange = useCallback(
    <K extends StepKey>(key: K, value: FunnelContext[K]) => {
      if (!funnelState) return;
      setFunnelState((prev) => {
        if (!prev) return prev;
        return { ...prev, [key]: value };
      });
    },
    [funnelState]
  );

  const { handleValidChange } = useFunnelController({
    curStep,
    setCurStep,
    stepKeys,
    onValidChange,
    validators,
  });

  return {
    funnelState,
    curStep,
    setCurStep,
    stepKeys,
    handleValidChange,
    isStepValid,
  };
};
