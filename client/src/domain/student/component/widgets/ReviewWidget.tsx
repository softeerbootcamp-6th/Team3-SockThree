import Button from "@/shared/components/Button";
import StarIcon from "@/assets/icons/default/yellow-star.svg?react";
import GradationChip from "@/shared/components/GradationChip";

type Review = {
  id: number;
  author: string;
  rating: number; // 0~5 (0.5 단위 지원)
  text: string;
  verifiedPercent?: number; // ex) 88 -> "88% 수강"
};

export interface ReviewWidgetsProps {
  size: "small" | "large";
  averageRating?: number; // ex) 4.9
  ratingCount?: number; // ex) 543
  bestReviews?: Review[]; // small은 1개, large는 3개 노출 권장
  onWriteClick?: () => void;
  onMoreClick?: () => void; // "더보기 >" 클릭
}

const ReviewWidgets = ({
  size,
  averageRating = 3.5,
  ratingCount = 131,
  bestReviews = [
    {
      id: 1,
      author: "홍길동",
      rating: 5,
      text: "오프라인이 아닌 온라인 수업이어서 걱정했는데 텍스트 더미입니다. 더길어지고있습니다있습니다",
      verifiedPercent: 100,
    },
    {
      id: 2,
      author: "김철수",
      rating: 2.5,
      text: "오프라인이 아닌 온라인 수업이어서 걱정했는데 텍스트 더미입니다.",
      verifiedPercent: 80,
    },
    {
      id: 3,
      author: "이영희",
      rating: 3.5,
      text: "오프라인이 아닌 온라인 수업이어서 걱정했는데 텍스트 더미입니다. 어쩌고고고고고고고",
      verifiedPercent: 50,
    },
  ],
  onWriteClick,
  onMoreClick,
}: ReviewWidgetsProps) => {
  if (size === "small") {
    const item = bestReviews[0];
    return (
      <div className="flex h-[20.5rem] w-[20.5rem] flex-col items-center justify-center gap-[1rem] rounded-[1.25rem] bg-white p-[1.5rem]">
        {/* 헤더 */}
        <Header
          title="리뷰"
          subtitle="베스트 리뷰"
          averageRating={averageRating}
          ratingCount={ratingCount}
        />

        {/* 카드 */}
        {item ? (
          <ReviewCard
            author={item.author}
            rating={item.rating}
            text={item.text}
            verifiedPercent={item.verifiedPercent}
          />
        ) : (
          <div className="typo-body-6 flex h-full w-full items-center justify-center text-gray-500">
            아직 리뷰가 없습니다.
          </div>
        )}

        <div className="flex w-full justify-end">
          <button
            type="button"
            onClick={onMoreClick}
            className="typo-label-4 cursor-pointer px-2 text-gray-500 hover:text-main-500"
          >
            더보기 &gt;
          </button>
        </div>
        <Button
          className="w-full"
          variant="outline"
          size="lgFlexible"
          onClick={onWriteClick}
        >
          리뷰 작성하기
        </Button>
      </div>
    );
  }

  if (size === "large") {
    const list = bestReviews.slice(0, 3);
    return (
      <div className="flex h-[20.5rem] w-[63.5rem] flex-col gap-[1rem] rounded-[1.25rem] bg-white p-[1.5rem]">
        {/* 헤더 */}
        <Header
          title="리뷰"
          subtitle="베스트 리뷰 3개"
          averageRating={averageRating}
          ratingCount={ratingCount}
        />

        {/* 카드 3열 */}
        <div className="grid grid-cols-3 gap-[1rem]">
          {list.map((r) => (
            <ReviewCard
              key={r.id}
              author={r.author}
              rating={r.rating}
              text={r.text}
              verifiedPercent={r.verifiedPercent}
            />
          ))}
        </div>

        <div className="flex w-full justify-end">
          <button
            type="button"
            onClick={onMoreClick}
            className="typo-label-4 cursor-pointer px-2 text-gray-500 hover:text-main-500"
          >
            더보기 &gt;
          </button>
        </div>

        <Button variant="outline" size="lgFlexible" onClick={onWriteClick}>
          리뷰 작성하기
        </Button>
      </div>
    );
  }

  return null;
};

export default ReviewWidgets;

/* =====================
 * Sub Components
 * ===================== */

const Header = ({
  title,
  subtitle,
  averageRating,
  ratingCount,
}: {
  title: string;
  subtitle: string;
  averageRating: number;
  ratingCount: number;
}) => {
  return (
    <div className="flex w-full items-start justify-between">
      <div className="flex flex-col gap-[1rem]">
        <span className="typo-title-6 text-gray-900">{title}</span>
        <span className="typo-label-4 text-gray-500">{subtitle}</span>
      </div>
      <div className="flex items-center gap-[0.25rem]">
        <span className="typo-label-4 text-gray-500">평균 평점</span>
        <StarIcon className="h-[1rem] w-[1rem]" />
        <span className="typo-label-0 text-gray-900">{averageRating}</span>
        <span className="typo-label-4 text-gray-500">({ratingCount})</span>
      </div>
    </div>
  );
};

const ReviewCard = ({
  author,
  rating,
  text,
  verifiedPercent = 0,
}: {
  author: string;
  rating: number; // 0~5 (0.5 단위 지원)
  text: string;
  verifiedPercent?: number;
}) => {
  return (
    <div className="rounded-[0.75rem] border border-gray-300 bg-white p-[0.75rem]">
      {/* 상단: 작성자 / 별점 / 칩 */}
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-[0.375rem]">
          <span className="typo-label-3 text-gray-900">{author}</span>
          <StarRating value={rating} />
          <span className="typo-label-4 text-gray-500">
            {rating.toFixed(1)}
          </span>
        </div>
        <GradationChip>
          <div className="typo-label-4 flex gap-[0.25rem] text-gray-600">
            <span className="typo-label-3 text-gray-900">
              {verifiedPercent}%
            </span>
            <span>수강</span>
          </div>
        </GradationChip>
      </div>

      {/* 본문 */}
      <p className="typo-body-6 mt-[0.5rem] line-clamp-2 text-gray-700">
        {text}
      </p>
    </div>
  );
};

const StarRating = ({ value }: { value: number }) => {
  // 안전 범위 보정
  const v = Math.max(0, Math.min(5, value ?? 0));

  return (
    <span
      className="inline-flex items-center gap-[2px]"
      aria-label={`별점 ${v}점 (5점 만점)`}
    >
      {Array.from({ length: 5 }).map((_, i) => {
        // 현재 칸의 채움 비율(0~100)
        const fill = Math.max(0, Math.min(100, (v - i) * 100));
        // 1칸을 넘어가면 100, 음수면 0으로 자동 보정
        const pct = fill >= 100 ? 100 : fill <= 0 ? 0 : fill;

        return (
          <span
            key={i}
            className="relative inline-block h-[0.875rem] w-[0.875rem]"
            aria-hidden="true"
          >
            <StarIcon className="absolute inset-0 h-[0.875rem] w-[0.875rem] opacity-30" />
            <span
              className="absolute inset-0 overflow-hidden"
              style={{ width: `${pct}%` }}
            >
              <StarIcon className="h-[0.875rem] w-[0.875rem]" />
            </span>
          </span>
        );
      })}
    </span>
  );
};
