import ProgressBar from "@/shared/components/ProgressBar";
import CheckIcon from "@/assets/icons/default/rotated-check.svg?react";

interface FunnelHeaderProps {
  currentIndex: number;
  totalSteps: number;
}

const FunnerHeader = ({ currentIndex, totalSteps }: FunnelHeaderProps) => {
  return (
    <header className="sticky top-0 z-20 flex items-center gap-[2rem] bg-bg py-[4rem] pr-10">
      <div className="flex items-center gap-2.5">
        <h1 className="typo-title-0">강좌 만들기</h1>
        <CheckIcon />
      </div>
      <ProgressBar
        current={currentIndex}
        max={totalSteps}
        type="percent"
        size="md"
      />
      <div className="flex h-fit items-center gap-2.5 rounded-full bg-gradient-to-r from-[#A0EACF] to-[#7DA7F9] px-2 py-2">
        <span className="flex h-6 w-6 justify-center rounded-full bg-white text-[#0066FF]">
          !
        </span>
        <span className="typo-label-3 text-white">
          강좌 개설까지 거의 다 왔어요
        </span>
      </div>
    </header>
  );
};

export default FunnerHeader;
