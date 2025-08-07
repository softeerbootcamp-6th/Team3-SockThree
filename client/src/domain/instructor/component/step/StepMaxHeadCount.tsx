import { useEffect, useRef, useState } from 'react';
import MaxHeadCountSlider from '@/domain/instructor/component/Slider.tsx';

interface StepMaxHeadCountProps {
  value?: number;
  onValidSubmit: (count: number) => void;
}

const StepMaxHeadCount = ({ value, onValidSubmit }: StepMaxHeadCountProps) => {
  const INIT_COUNT = 35;
  const [maxHeadCount, setMaxHeadCount] = useState<number>(value ?? INIT_COUNT);
  const debounceRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const handleChange = (val: number) => {
    setMaxHeadCount(val);

    if (debounceRef.current) {
      clearTimeout(debounceRef.current);
    }

    debounceRef.current = setTimeout(() => {
      if (val > 0) {
        onValidSubmit(val);
      }
    }, 300);
  };

  // 컴포넌트 unmount 시 debounce 클리어
  useEffect(() => {
    return () => {
      if (debounceRef.current) {
        clearTimeout(debounceRef.current);
      }
    };
  }, []);

  return (
    <div className="flex w-full flex-col gap-[90px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">최대 몇 명까지 수강할 수 있나요?</p>

      <div className="flex flex-col gap-6">
        <div className="w-full max-w-xl space-y-4">
          <MaxHeadCountSlider value={maxHeadCount} onChange={handleChange} />
        </div>
      </div>
    </div>
  );
};

export default StepMaxHeadCount;