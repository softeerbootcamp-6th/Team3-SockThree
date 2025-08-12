import PlayIcon from "@/assets/icons/gradient/video-play.svg?react";
import { useState } from "react";

export interface NextLectureWidgetProps {
  size: "small" | "large";
  title?: string; // 섹션 타이틀 (ex. "다음 강좌")
  lessonTitle?: string; // 마지막으로 시청하던 강좌명
  description?: string; // 요약/소개
  thumbnailUrl?: string; // hover 시 교체될 실제 썸네일
  onClickVideo?: () => void; // 비디오 영역 클릭
}

const NextLectureWidget = ({
  size = "small",
  title = "다음 강좌",
  lessonTitle = "골프의 기초: 두번째 레슨",
  description = `강좌 개요입니다. 개행합니다. \n 텍스트 더미 입니다. 강좌 개요입니다. 강좌 개요입니다. 골프에 대해서 배우는 두번째 시간입니다. 이번에 정말 중요합니다.`,
  thumbnailUrl,
  onClickVideo,
}: NextLectureWidgetProps) => {
  if (size === "small") {
    return (
      <div className="flex h-[20.5rem] w-[20.5rem] flex-col gap-[1rem] rounded-[1.25rem] bg-white p-[1.5rem]">
        {/* 제목 */}
        <h3 className="typo-title-5 text-gray-900">{title}</h3>
        <h4 className="typo-label-0 text-gray-900">{lessonTitle}</h4>
        {/* 간단 설명 */}
        <p className="typo-body-6 line-clamp-2 text-gray-600">{`${description}`}</p>
        {/* 비디오 프리뷰 */}

        <VideoPreview thumbnailUrl={thumbnailUrl} onClick={onClickVideo} />
      </div>
    );
  }

  if (size === "large") {
    return (
      <div className="flex h-[20.5rem] w-[63.5rem] items-start gap-[1.25rem] rounded-[1.25rem] bg-white p-[1.5rem]">
        {/* 좌측 비디오 */}
        <div className="w-[31.1rem]">
          <VideoPreview thumbnailUrl={thumbnailUrl} onClick={onClickVideo} />
        </div>

        {/* 우측 텍스트 */}
        <div className="flex min-w-0 flex-1 flex-col gap-[1rem]">
          <h3 className="typo-title-5 text-gray-900">{title}</h3>
          <h4 className="typo-title-3 text-gray-900">{lessonTitle}</h4>
          <p className="typo-body-6 line-clamp-5 leading-5 whitespace-pre-line text-gray-600">
            {description}
          </p>
        </div>
      </div>
    );
  }

  return null;
};

export default NextLectureWidget;

const VideoPreview = ({
  thumbnailUrl,
  onClick,
}: {
  thumbnailUrl?: string;
  onClick?: () => void;
}) => {
  const [hovered, setHovered] = useState(false);

  const frame = "rounded-[0.625rem] bg-gradient-main p-[1px]";
  const inner = "relative w-full overflow-hidden rounded-[0.5rem] bg-white";

  return (
    <button
      type="button"
      onClick={onClick}
      onMouseEnter={() => setHovered(true)}
      onMouseLeave={() => setHovered(false)}
      className={`group relative w-full ${frame}`}
    >
      <div className={`${inner}`} style={{ aspectRatio: "16 / 9" }}>
        {/* 배경: hover 시 썸네일 */}
        {hovered && thumbnailUrl ? (
          <div
            className="absolute inset-0 cursor-pointer bg-gradient-sub2"
            style={{
              backgroundImage: `url(${thumbnailUrl})`,
              backgroundSize: "cover",
              backgroundPosition: "center",
            }}
          />
        ) : (
          <div className="absolute inset-0 cursor-pointer bg-gradient-sub2" />
        )}

        {/* 재생 아이콘 */}
        <PlayIcon className="pointer-events-none absolute top-1/2 left-1/2 h-10 w-10 -translate-x-1/2 -translate-y-1/2 opacity-90 transition-transform duration-200 group-hover:scale-130" />
      </div>
    </button>
  );
};
