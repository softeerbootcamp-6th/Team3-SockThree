import { Slider } from "@/shared/shadcn/ui/slider.tsx";
import BalloonIcon from "@/assets/icons/gradient/balloon-tooltip.svg?react";

interface MaxHeadCountSliderProps {
  value: number;
  onChange: (value: number) => void;
}

const MaxHeadCountSlider = ({ value, onChange }: MaxHeadCountSliderProps) => {
  return (
    <div className="relative">
      <div className="relative w-full">
        <Slider
          value={[value]}
          max={50}
          min={1}
          step={1}
          onValueChange={(val) => onChange(val[0])}
          className="w-full"
        />
        <div
          className="absolute -top-[80px] flex h-[59px] w-[60px] items-center justify-center"
          style={{
            left: `${(value / 50) * 100 - 1}%`,
            transform: "translateX(-50%)",
          }}
        >
          <BalloonIcon className="absolute inset-0" />
          <span className="typo-body-4 relative z-1 text-black">{value}명</span>
        </div>
      </div>
      <div className="mt-2 flex justify-between px-1 text-sm text-gray-500">
        <span />
        <span>최대인원 50명</span>
      </div>
    </div>
  );
};

export default MaxHeadCountSlider;
