import { useEffect, useRef, useState } from "react";
import Chips from "@/shared/components/Chips.tsx";
import { useFormContext } from "react-hook-form";

interface StepPriceProps {
  onNext: () => void;
}

const StepPrice = ({ onNext }: StepPriceProps) => {
  const [customPrice, setCustomPrice] = useState(""); // 표시용 (localeString)

  const inputRef = useRef<HTMLInputElement | null>(null);
  const debounceRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const {
    watch,
    setValue,
    trigger,
    register,
    formState: { errors },
  } = useFormContext();

  const predefinedPrices = [0, 10000, 30000, 50000, 100000];

  const price = watch("price");

  // 초기 가격 설정 (이미 price가 설정되어 있는 경우)
  useEffect(() => {
    if (price !== undefined) {
      setCustomPrice(price.toLocaleString());
    }
  }, []);

  // ✅ Chips 클릭 시 input만 반영 + 포커스
  const handlePredefinedClick = (selectedPrice: number) => {
    setCustomPrice(selectedPrice.toLocaleString());
    setValue("price", selectedPrice, { shouldValidate: true });
    inputRef.current?.focus();

    // 디바운스 onNext
    if (debounceRef.current) clearTimeout(debounceRef.current);
    debounceRef.current = setTimeout(async () => {
      const isValid = await trigger("price");
      if (isValid) {
        onNext();
      }
    }, 300);
  };

  // ✅ input 값 변경 → 숫자로 파싱 → setValue → 디바운스 onNext
  const handleCustomChange = (value: string) => {
    const rawValue = value.replace(/,/g, "");
    const parsed = parseInt(rawValue);

    // 표시용 상태 업데이트
    if (!isNaN(parsed)) {
      setCustomPrice(parsed.toLocaleString());
      setValue("price", parsed, { shouldValidate: true });
    } else {
      setCustomPrice("");
      setValue("price", undefined, { shouldValidate: true });
    }

    // 디바운스 onNext
    if (debounceRef.current) clearTimeout(debounceRef.current);
    debounceRef.current = setTimeout(async () => {
      const isValid = await trigger("price");
      if (isValid) {
        onNext();
      }
    }, 300);
  };

  useEffect(() => {
    register("price", {
      required: "가격을 입력해주세요",
      min: { value: 0, message: "0원 이상 입력해주세요" },
    });
  }, [register]);

  const formatPrice = (price: number) =>
    price === 0 ? "무료" : `${price.toLocaleString()}원`;

  return (
    <div className="flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌 가격을 설정해주세요</p>

      <div className="flex flex-col gap-6">
        {/* Chips */}
        <div className="flex flex-wrap gap-4">
          {predefinedPrices.map((priceOption) => (
            <Chips
              key={priceOption}
              type="field"
              title={formatPrice(priceOption)}
              selected={price === priceOption}
              onClick={() => handlePredefinedClick(priceOption)}
            />
          ))}
        </div>

        {/* Input */}
        <div className="flex items-center gap-4">
          <span>직접 입력:</span>
          <input
            ref={inputRef}
            type="text"
            value={customPrice}
            onChange={(e) => handleCustomChange(e.target.value)}
            placeholder="가격 입력 (원)"
            className="rounded-lg border px-4 py-2"
            inputMode="numeric"
          />
        </div>

        {/* 에러 메시지 */}
        {errors.price && (
          <p className="text-sm text-red-500">
            {errors.price.message as string}
          </p>
        )}
      </div>
    </div>
  );
};

export default StepPrice;
