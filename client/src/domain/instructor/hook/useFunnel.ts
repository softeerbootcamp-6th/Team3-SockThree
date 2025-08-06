import { useState } from "react";

export function useFunnel<T>(steps: Array<keyof T>) {
  const [step, setStep] = useState<keyof T>(steps[0]);

  const currentIndex = steps.indexOf(step);

  const goNextStep = () => {
    const nextStep = steps[currentIndex + 1];
    if (nextStep) setStep(nextStep);
  };

  return { step, goNextStep, currentIndex };
}
