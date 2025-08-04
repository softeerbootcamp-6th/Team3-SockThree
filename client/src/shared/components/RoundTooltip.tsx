import RoundToolTipSVG from "@/assets/icons/tooltip/tooltip-round.svg?react";
import * as React from "react";

interface RoundTooltipProps {
  text: string;
}

const RoundTooltip = ({ text }: RoundTooltipProps) => {
  return (
    <RoundTooltipContainer>
      <span className="typo-body-5 text-center whitespace-normal text-white">
        {text}
      </span>
    </RoundTooltipContainer>
  );
};

interface RoundTooltipContainerProps {
  children: React.ReactNode;
}

const RoundTooltipContainer = ({ children }: RoundTooltipContainerProps) => {
  return (
    <div className="relative inline-block h-auto w-[9.5625rem]">
      <RoundToolTipSVG className="block" />
      <div className="absolute inset-0 flex items-center justify-center pt-[0.75rem] pb-[1rem]">
        {children}
      </div>
    </div>
  );
};

export default RoundTooltip;
