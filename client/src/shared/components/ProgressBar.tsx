import { tv } from "tailwind-variants";
import { useEffect, useState } from "react";

const progressBarVariant = tv({
  slots: {
    root: "flex items-center justify-between gap-1",
    track:
      "relative w-full h-[.5625rem] bg-gray-300 rounded-[50px] overflow-hidden",
    fill: "rounded-[50px] absolute top-0 left-0 h-full bg-gradient-to-r from-[#26caba] to-[#0850fd] transition-all duration-500",
    label: "text-right",
  },
  variants: {
    size: {
      md: {
        root: "w-[20rem]",
      },
      lg: {
        root: "w-[39rem]",
      },
    },
    type: {
      percent: {
        label: "typo-title-5 text-black",
      },
      ratio: {
        label: "text-white typo-body-5",
      },
    },
  },
  defaultVariants: {
    size: "md",
    type: "percent",
  },
});

interface ProgressBarProps {
  value?: number;
  current?: number;
  max?: number;
  labelText?: string;
  type?: "percent" | "ratio";
  size?: "md" | "lg";
}

const ProgressBar = ({
  value,
  current,
  max,
  labelText,
  type,
  size = "md",
}: ProgressBarProps) => {
  // value가 주어지지 않았을 경우 current와 max를 이용해 계산
  const computedValue = computeValue(value ?? 0, current, max);

  const { root, track, fill, label } = progressBarVariant({
    size,
    type,
  });

  const [progress, setProgress] = useState(0);

  useEffect(() => {
    setProgress(computedValue);
  }, [computedValue]);

  const defaultLabel =
    type === "ratio" && current !== undefined && max !== undefined
      ? `${current}/${max}`
      : `${Math.round(computedValue)}%`;

  return (
    <div className={root()}>
      <div className={track()}>
        <div className={fill()} style={{ width: `${progress}%` }} />
      </div>
      <span className={label()}>{labelText ?? defaultLabel}</span>
    </div>
  );
};

export default ProgressBar;

const computeValue = (value: number, current?: number, max?: number) => {
  if (value) return value;
  if (!current || !max) return 0;
  return (current / max) * 100;
};
