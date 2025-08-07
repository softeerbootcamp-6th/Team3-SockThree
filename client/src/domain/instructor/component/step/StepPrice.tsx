import { useEffect, useRef, useState } from 'react';
import Chips from '@/shared/components/Chips.tsx';

interface StepPriceProps {
  value?: number;
  onValidSubmit: (price: number) => void;
}

const StepPrice = ({ value, onValidSubmit }: StepPriceProps) => {
  const inputRef = useRef<HTMLInputElement | null>(null);
  const debounceRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const predefinedPrices = [0, 10000, 30000, 50000, 100000];
  const [customPriceText, setCustomPriceText] = useState('');

  // 초기값 세팅
  useEffect(() => {
    if (value !== undefined) {
      setCustomPriceText(value.toLocaleString());
    }
  }, [value]);

  // 디바운스로 제출
  const submitDebounced = (price: number) => {
    if (debounceRef.current) clearTimeout(debounceRef.current);
    debounceRef.current = setTimeout(() => {
      onValidSubmit(price);
    }, 300);
  };

  const handlePredefinedClick = (selectedPrice: number) => {
    setCustomPriceText(selectedPrice.toLocaleString());
    inputRef.current?.focus();
    submitDebounced(selectedPrice);
  };

  const handleCustomChange = (rawInput: string) => {
    const rawValue = rawInput.replace(/,/g, '');
    const parsed = parseInt(rawValue, 10);

    // 상태 업데이트
    setCustomPriceText(rawInput);

    if (!isNaN(parsed) && parsed >= 0) {
      submitDebounced(parsed);
    }
  };

  const formatPrice = (price: number) =>
    price === 0 ? '무료' : `${price.toLocaleString()}원`;

  return (
    <div className='flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]'>
      <p className='typo-title-5'>강좌 가격을 설정해주세요</p>

      <div className='flex flex-col gap-6'>
        {/* Chips */}
        <div className='flex flex-wrap gap-4'>
          {predefinedPrices.map((priceOption) => (
            <Chips
              key={priceOption}
              type='field'
              title={formatPrice(priceOption)}
              selected={value === priceOption}
              onClick={() => handlePredefinedClick(priceOption)}
            />
          ))}
        </div>

        {/* Input */}
        <div className='flex items-center gap-4'>
          <span>직접 입력:</span>
          <input
            ref={inputRef}
            type='text'
            value={customPriceText}
            onChange={(e) => handleCustomChange(e.target.value)}
            placeholder='가격 입력 (원)'
            className='rounded-lg border px-4 py-2'
            inputMode='numeric'
          />
        </div>
      </div>
    </div>
  );
};

export default StepPrice;