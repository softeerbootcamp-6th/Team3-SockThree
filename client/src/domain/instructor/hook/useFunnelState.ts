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

  const validators = {
    category: (value: string) => !!value,
    subCategory: (value: string) => !!value,
    level: (value: string) => !!value,
    duration: (value: { startDate?: string; endDate?: string }) =>
      !!value.startDate && !!value.endDate,
    maxHeadCount: (value: number) => value > 0,
    uploadTimes: (value: string[]) => value.length > 0,
    price: (value: number) => value >= 0,
    introduction: (value: {
      name: string;
      description: string;
      simpleDescription: string;
    }) => !!value.name && !!value.description && !!value.simpleDescription,
    curriculum: (value: string) => !!value,
    imageUrl: (value: string) => !!value,
  };

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
  };
};
