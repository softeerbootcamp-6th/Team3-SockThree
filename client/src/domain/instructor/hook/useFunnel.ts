import { useState } from "react";

export function useFunnel<T>(steps: Array<keyof T>) {
  const [step, setStep] = useState<keyof T>(steps[0]);

  const goNextStep = () => {
    const currentIndex = steps.indexOf(step);
    const nextStep = steps[currentIndex + 1];
    if (nextStep) setStep(nextStep);
  };

  const goToStep = (target: keyof T) => {
    if (steps.includes(target)) {
      setStep(target);
      const el = document.getElementById(`step-${String(target)}`);
      if (el) {
        el.scrollIntoView({ behavior: "smooth", block: "start" });
      }
    }
  };

  return { step, goNextStep, goToStep };
}
