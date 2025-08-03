import { useState } from "react";

// 타입 정의
type Step = 1 | 2 | 3 | 4 | 5 | 6 | 7;
type Context = {
  category?: string;
  subCategory?: string;
  price?: string;
  schedule?: string;
  days?: string[];
  headCount?: number;
  imageUrl?: string;
};

// 상위 컴포넌트
export default function FunnelForm() {
  const [step, setStep] = useState<Step>(1);
  const [context, setContext] = useState<Context>({});

  const onNextStep = <K extends keyof Context>(
    key: K,
    value: Context[K],
    nextStep: Step,
    shouldReset?: (prev: Context[K] | undefined, next: Context[K]) => boolean,
    resetKeys: Array<keyof Context> = []
  ) => {
    setContext((prev) => {
      const needsReset = shouldReset?.(prev[key], value);
      const reset = needsReset
        ? Object.fromEntries(resetKeys.map((k) => [k, undefined]))
        : {};
      return { ...prev, ...reset, [key]: value };
    });
    if (step < nextStep) setStep(nextStep);
  };

  return (
    <div>
      <StepCategory
        onNextStep={({ category }) =>
          onNextStep("category", category, 2, (prev, next) => prev !== next, [
            "subCategory",
            "price",
            "schedule",
            "days",
            "headCount",
            "imageUrl",
          ])
        }
      />
      {step >= 2 && context.category && (
        <StepSubCategory
          category={context.category}
          onNextStep={({ subCategory }) =>
            onNextStep("subCategory", subCategory, 3)
          }
        />
      )}
      {step >= 3 && context.subCategory && (
        <StepPrice onNextStep={({ price }) => onNextStep("price", price, 4)} />
      )}
      {step >= 4 && context.price && (
        <StepSchedule
          onNextStep={({ schedule }) => onNextStep("schedule", schedule, 5)}
        />
      )}
      {step >= 5 && context.schedule && (
        <StepDays onNextStep={({ days }) => onNextStep("days", days, 6)} />
      )}
      {step >= 6 && context.days && (
        <StepHeadCount
          onNextStep={({ headCount }) => onNextStep("headCount", headCount, 7)}
        />
      )}
      {step >= 7 && context.headCount && (
        <StepImageUpload
          onNextStep={({ imageUrl }) => onNextStep("imageUrl", imageUrl, 7)}
        />
      )}
    </div>
  );
}

// Step 1: 카테고리
function StepCategory({
  onNextStep,
}: {
  onNextStep: (context: { category: string }) => void;
}) {
  const [selected, setSelected] = useState<string | null>(null);
  const options = ["운동", "미술", "음악", "요리"];
  return (
    <div>
      <h2>1단계: 카테고리</h2>
      {options.map((option) => (
        <button
          key={option}
          onClick={() => {
            setSelected(option);
            onNextStep({ category: option });
          }}
          style={{
            marginRight: 8,
            background: selected === option ? "#4caf50" : "#eee",
          }}
        >
          {option}
        </button>
      ))}
    </div>
  );
}

// Step 2: 세부 카테고리
function StepSubCategory({
  category,
  onNextStep,
}: {
  category: string;
  onNextStep: (context: { subCategory: string }) => void;
}) {
  const [selected, setSelected] = useState<string | null>(null);

  const optionsMap: Record<string, string[]> = {
    운동: ["헬스", "요가"],
    미술: ["수채화", "크로키"],
    음악: ["기타", "보컬"],
    요리: ["한식", "양식"],
  };

  const options = optionsMap[category] || [];

  return (
    <div>
      <h2>2단계: 세부 카테고리</h2>
      {options.map((option) => (
        <button
          key={option}
          onClick={() => {
            setSelected(option);
            onNextStep({ subCategory: option });
          }}
          style={{
            marginRight: 8,
            background: selected === option ? "#2196f3" : "#eee",
          }}
        >
          {option}
        </button>
      ))}
    </div>
  );
}

// Step 3: 가격
function StepPrice({
  onNextStep,
}: {
  onNextStep: (context: { price: string }) => void;
}) {
  const [price, setPrice] = useState("");

  return (
    <div>
      <h2>3단계: 가격</h2>
      <input
        type="number"
        value={price}
        onChange={(e) => setPrice(e.target.value)}
      />
      <button onClick={() => onNextStep({ price })} disabled={!price}>
        다음
      </button>
    </div>
  );
}

// Step 4: 스케줄
function StepSchedule({
  onNextStep,
}: {
  onNextStep: (context: { schedule: string }) => void;
}) {
  const [schedule, setSchedule] = useState("");

  return (
    <div>
      <h2>4단계: 스케줄</h2>
      <input
        type="text"
        placeholder="예: 매주 수요일 오후 2시"
        value={schedule}
        onChange={(e) => setSchedule(e.target.value)}
      />
      <button onClick={() => onNextStep({ schedule })} disabled={!schedule}>
        다음
      </button>
    </div>
  );
}

// Step 5: 요일 선택
function StepDays({
  onNextStep,
}: {
  onNextStep: (context: { days: string[] }) => void;
}) {
  const [selected, setSelected] = useState<string[]>([]);
  const options = ["월", "화", "수", "목", "금", "토", "일"];

  const toggle = (day: string) => {
    setSelected((prev) =>
      prev.includes(day) ? prev.filter((d) => d !== day) : [...prev, day]
    );
  };

  return (
    <div>
      <h2>5단계: 요일 선택</h2>
      {options.map((day) => (
        <button
          key={day}
          onClick={() => toggle(day)}
          style={{
            marginRight: 8,
            background: selected.includes(day) ? "#ff9800" : "#eee",
          }}
        >
          {day}
        </button>
      ))}
      <button
        onClick={() => onNextStep({ days: selected })}
        disabled={!selected.length}
      >
        다음
      </button>
    </div>
  );
}

// Step 6: 인원 수
function StepHeadCount({
  onNextStep,
}: {
  onNextStep: (context: { headCount: number }) => void;
}) {
  const [count, setCount] = useState(1);

  return (
    <div>
      <h2>6단계: 인원 수</h2>
      <input
        type="number"
        min={1}
        value={count}
        onChange={(e) => setCount(Number(e.target.value))}
      />
      <button onClick={() => onNextStep({ headCount: count })}>다음</button>
    </div>
  );
}

// Step 7: 이미지 업로드
function StepImageUpload({
  onNextStep,
}: {
  onNextStep: (context: { imageUrl: string }) => void;
}) {
  const [imageUrl, setImageUrl] = useState("");

  return (
    <div>
      <h2>7단계: 이미지 업로드</h2>
      <input
        type="text"
        placeholder="이미지 URL 입력"
        value={imageUrl}
        onChange={(e) => setImageUrl(e.target.value)}
      />
      <button onClick={() => onNextStep({ imageUrl })} disabled={!imageUrl}>
        완료
      </button>
    </div>
  );
}
