import Button from "@/shared/components/Button";
import { useState } from "react";

import HeartIcon from "@/assets/icons/default/heart.svg?react";
import CircleButton from "@/shared/components/CircleButton";
import YellowStartIcon from "@/assets/icons/default/yellow-star.svg?react";

interface CourseDetailCardProps {
  imageUrl?: string;
  teacherName?: string;
  title?: string;
  days?: string[];
  startTime?: string;
  endTime?: string;
  rating?: number;
  reviews?: number;
  price?: number;
  defaultFavorite?: boolean;
}

const CourseDetailCard = ({
  imageUrl = "https://cdn.pixabay.com/photo/2025/04/25/12/13/nature-9558835_1280.jpg",
  teacherName = "홍길동",
  title = "프론트엔드 개발자 양성 과정",
  days = ["월", "수", "금"],
  startTime = "10:00",
  endTime = "12:00",
  rating = 4.5,
  reviews = 120,
  price = 2000000,
  defaultFavorite = false,
}: CourseDetailCardProps) => {
  const [favorite, setFavorite] = useState(defaultFavorite);

  const toggleFavorite = () => {
    setFavorite((prev) => !prev);
    // 여기에 즐겨찾기 상태 변경 로직 추가 (예: API 호출)
  };

  return (
    <div className="group relative flex h-[32.3125rem] w-[26.9375rem] flex-col gap-[.9375rem] overflow-clip rounded-[1.25rem] bg-white px-[1.375rem] py-[1.6563rem] transition-all duration-300 hover:shadow-main">
      {/* 호버 시 카드 위(z)에 뜨는 버튼 */}
      <div className="absolute top-[19.0625rem] right-[2.3125rem] z-20 translate-y-2 opacity-0 transition-all duration-300 group-hover:translate-y-0 group-hover:opacity-100">
        <Button
          variant="outline"
          className="typo-label-1 h-[3.3125rem] w-[6.25rem] cursor-pointer"
        >
          상세 보기
        </Button>
      </div>

      {/* 이미지 영역 */}
      <div className="relative">
        <img
          src={imageUrl}
          alt={title}
          className="h-[19.75rem] w-full rounded-[1.25rem] object-cover"
        />

        {/* 하트 버튼 (즐겨찾기) */}
        <div className="absolute top-[1.4375rem] right-[1.4375rem] z-10">
          <CircleButton
            variant="ghost"
            onClick={toggleFavorite}
            icon={
              favorite ? (
                <HeartIcon className="w-[2.375rem] text-main-500" /> // 채워진 하트 아이콘
              ) : (
                <HeartIcon className="w-[2.375rem] text-white/50" />
              )
            }
          />
        </div>
      </div>

      {/* 내용 영역 */}
      <div className="flex h-full flex-col gap-[.5625rem]">
        <span className="typo-label-2 whitespace-nowrap text-gray-500">
          {teacherName}
        </span>
        <span className="typo-title-3 truncate whitespace-nowrap">{title}</span>
        <div className="flex items-center gap-[.9375rem]">
          <span className="typo-body-6">{days.join(" ")}</span>
          <span className="typo-body-6">
            {startTime} ~ {endTime}
          </span>
        </div>

        {/* 별점 */}
        <div className="flex items-end justify-between">
          <div className="typo-label-2 flex items-center gap-1">
            <YellowStartIcon className="h-[1.125rem] w-[1.125rem] text-yellow-400" />
            <span>{rating.toFixed(1)}</span>
            <span className="text-gray-500">({reviews})</span>
          </div>

          {/* 가격 */}
          <div className="flex items-end gap-2">
            {price === 0 ? (
              <span className="typo-title-3 text-right">무료</span>
            ) : (
              <>
                <span className="typo-title-3 text-right">
                  {price.toLocaleString()}
                </span>
                <span className="typo-body-5">/ 시간</span>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CourseDetailCard;
