import { useState, useCallback } from "react";

export function useFunnel<T extends Record<string, unknown>>() {
  const [step, setStep] = useState(1);
  const [context, setContext] = useState<Partial<T>>({});

  const pushStep = useCallback(<K extends keyof T>(key: K, value: T[K]) => {
    setContext((prev) => ({
      ...prev,
      [key]: value,
    }));
    setStep((prev) => prev + 1);
  }, []);

  return {
    step,
    context,
    pushStep,
  };
}
