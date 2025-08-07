import * as React from "react";

interface StepRendererProps {
  currentStep: number;
  step: number;
  children: React.ReactNode;
}

export const StepRenderer = ({
  currentStep,
  step,
  children,
}: StepRendererProps) => {
  return currentStep >= step ? <>{children}</> : null;
};
