import { tv } from "tailwind-variants";
import { useEffect, useState } from "react";

const progressBarVariant = tv({
  slots: {
    root: "flex items-center justify-between gap-1",
    track: "relative w-full h-[.5625rem] rounded-[50px] overflow-hidden",
    fill: "rounded-[50px] absolute top-0 left-0 h-full transition-all duration-500",
    label: "text-right",
  },
  variants: {
    size: {
      xs: {
        root: "w-[6.25rem]",
      },
      sm: {
        root: "w-[18.75rem]",
      },
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
      smallRatio: {
        label: "typo-label-1",
      },
      smallPercent: {
        label: "typo-label-1",
      },
    },
    color: {
      gradient: {
        fill: "bg-gradient-to-r from-[#26caba] to-[#0850fd]",
        track: "bg-gray-300",
      },
      mono: { fill: "bg-white", track: "bg-white/50" },
    },
  },
  defaultVariants: {
    size: "md",
    type: "percent",
    color: "gradient",
  },
});

interface ProgressBarProps {
  value?: number;
  current?: number;
  max?: number;
  labelText?: string;
  type?: "percent" | "ratio" | "smallRatio" | "smallPercent";
  size?: "md" | "lg" | "xs" | "sm";
  color?: "gradient" | "mono";
}

const ProgressBar = ({
  value,
  current,
  max,
  labelText,
  type,
  size = "md",
  color = "gradient",
}: ProgressBarProps) => {
  const computedValue = computeValue(value ?? 0, current, max);

  const { root, track, fill, label } = progressBarVariant({
    size,
    type,
    color,
  });

  const [progress, setProgress] = useState(0);

  useEffect(() => {
    setProgress(computedValue);
  }, [computedValue]);

  const defaultLabel =
    (type === "ratio" || type === "smallRatio") &&
    current !== undefined &&
    max !== undefined
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
