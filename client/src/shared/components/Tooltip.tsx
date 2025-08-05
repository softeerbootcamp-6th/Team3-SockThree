import SoftStarIcon from "@/assets/icons/soft_star.svg?react";
import LeftTopTooltipSVG from "@/assets/icons/tooltip/tooltip_left_top.svg?react";
import BottomTooltipSVG from "@/assets/icons/tooltip/tooltip_bottom.svg?react";
import LeftBottomTooltipSVG from "@/assets/icons/tooltip/tooltip_left_bottom.svg?react";
import TopTooltipSVG from "@/assets/icons/tooltip/tooltip_top.svg?react";
import * as React from "react";

// 툴팁 방향 타입 정의
type TooltipVariant = "left_top" | "bottom" | "left_bottom" | "top";

// 툴팁 속성 정의
interface TooltipProps {
  title: string;
  label: string;
  variant?: TooltipVariant;
}

const Tooltip = ({ title, label, variant = "left_top" }: TooltipProps) => {
  return (
    <TooltipContainer variant={variant}>
      <div className="flex flex-col gap-[0.4rem]">
        <div className="flex items-center gap-[0.5rem]">
          <SoftStarIcon />
          <p className="typo-body-4">{title}</p>
        </div>
        <span className="typo-label-1 break-words whitespace-normal text-gray-800">
          {label}
        </span>
      </div>
    </TooltipContainer>
  );
};

interface TooltipContainerProps {
  children: React.ReactNode;
  variant: TooltipVariant;
}

const TooltipContainer = ({ children, variant }: TooltipContainerProps) => {
  // 각 variant에 맞는 SVG 컴포넌트 선택
  const getSVGComponent = () => {
    switch (variant) {
      case "bottom":
        return BottomTooltipSVG;
      case "left_bottom":
        return LeftBottomTooltipSVG;
      case "top":
        return TopTooltipSVG;
      default:
        return LeftTopTooltipSVG;
    }
  };

  // 각 variant별 콘텐츠 위치 조정
  const getContentStyles = () => {
    switch (variant) {
      case "bottom":
        return "absolute top-0 left-3 h-full";
      case "left_bottom":
        return "absolute top-0 left-3 h-full";
      case "top":
        return "absolute top-2 left-3 h-full";
      default:
        return "absolute top-0 left-3 h-full";
    }
  };

  const SVGComponent = getSVGComponent();
  const contentStyles = getContentStyles();

  return (
    <div className="relative inline-block w-[375px]">
      <SVGComponent className="block" />
      <div className={`${contentStyles} flex px-[0.75rem] py-[1rem]`}>
        {children}
      </div>
    </div>
  );
};

export default Tooltip;
