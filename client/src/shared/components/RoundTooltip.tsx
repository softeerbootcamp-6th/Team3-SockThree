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
    <div className="relative inline-flex items-center justify-center">
      <span className="px-[1.1875rem] pt-[.75rem] pb-[1.0075rem] whitespace-nowrap">
        {children}
      </span>
      <RoundToolTipSVG
        className="pointer-events-none absolute inset-0 h-full w-full"
        preserveAspectRatio="none"
      />
    </div>
  );
};

export default RoundTooltip;
