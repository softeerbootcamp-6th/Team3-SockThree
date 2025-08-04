// import RoundToolTipSVG from "@/assets/icons/tooltip/tooltip_round.svg?react";
// import * as React from "react";

// // 툴팁 속성 정의
// interface RoundTooltipProps {
//   text: string;
// }

// const RoundTooltip = ({ text }: RoundTooltipProps) => {
//   return (
//     <RoundTooltipContainer>
//       <div className="flex flex-col gap-[0.4rem]">
//         <span className="typo-label-1 break-words whitespace-normal text-gray-800">
//           {text}
//         </span>
//       </div>
//     </RoundTooltipContainer>
//   );
// };

// interface RoundTooltipContainerProps {
//   children: React.ReactNode;
// }

// const RoundTooltipContainer = ({ children }: RoundTooltipContainerProps) => {
//   return (
//     <div className="relative inline-block w-[9.5625rem]">
//       <RoundToolTipSVG className="block" />
//       <div className={`flex px-[1.5rem] pt-[.75rem]`}>{children}</div>
//     </div>
//   );
// };

// export default RoundTooltip;

import RoundToolTipSVG from "@/assets/icons/tooltip/tooltip_round.svg?react";
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
