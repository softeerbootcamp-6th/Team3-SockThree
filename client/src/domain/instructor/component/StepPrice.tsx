import { useState } from "react";
import Chips from "@/shared/components/Chips.tsx";

interface StepPriceProps {
  onNextStep: (context: { price: number }) => void;
}

const StepPrice = ({ onNextStep }: StepPriceProps) => {
  const [price, setPrice] = useState<number | null>(null);
  const [customPrice, setCustomPrice] = useState("");

  const predefinedPrices = [0, 10000, 30000, 50000, 100000];

  const handlePredefinedClick = (selectedPrice: number) => {
    setPrice(selectedPrice);
    setCustomPrice("");
    onNextStep({ price: selectedPrice });
  };

  const handleCustomSubmit = () => {
    const parsedPrice = parseInt(customPrice);
    if (parsedPrice >= 0) {
      setPrice(parsedPrice);
      onNextStep({ price: parsedPrice });
    }
  };

  const formatPrice = (price: number) => {
    return price === 0 ? "무료" : `${price.toLocaleString()}원`;
  };

  return (
    <div className="border- flex w-[71rem] flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌 가격을 설정해주세요</p>
      <div className="flex flex-col gap-6">
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
        <div className="flex items-center gap-4">
          <span>직접 입력:</span>
          <input
            type="number"
            value={customPrice}
            onChange={(e) => setCustomPrice(e.target.value)}
            placeholder="가격 입력 (원)"
            className="rounded-lg border px-4 py-2"
            min="0"
          />
          <button
            onClick={handleCustomSubmit}
            disabled={!customPrice || parseInt(customPrice) < 0}
            className="rounded-lg bg-blue-500 px-4 py-2 text-white disabled:bg-gray-300"
          >
            선택
          </button>
        </div>
      </div>
    </div>
  );
};

export default StepPrice;
