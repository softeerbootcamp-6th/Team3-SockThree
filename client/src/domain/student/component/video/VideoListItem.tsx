import TriangleRightIcon from "@/assets/icons/default/triangle-right.svg?react";
import ClockIcon from "@/assets/icons/default/clock.svg?react";
import { formatDate } from "@/shared/utils/dateUtils";

import { tv } from "tailwind-variants";

interface VideoListItemProps {
  index?: number;
  title?: string;
  duration?: number;
  updatedDate?: Date;
  watchedTime?: number;
  isCompleted?: boolean;
}

const VideoListItem = ({
  index = 1,
  title = "싹쓰리 골프의 시작",
  duration = 100,
  updatedDate = new Date(),
  watchedTime = 30,
  isCompleted = false,
}: VideoListItemProps) => {
  const formattedDate = formatDate(updatedDate, ".", true);

  const {
    container,
    left,
    playButton,
    playButtonIcon,
    title: titleCls,
  } = VideoListItemVariant({
    isCompleted,
  });

  return (
    <div className={container()}>
      {/* 왼쪽: 재생버튼 + 제목 + 시간 */}
      <div className={left()}>
        {/* 재생 버튼 */}
        <button className={playButton()}>
          <TriangleRightIcon className={playButtonIcon()} />
        </button>

        {/* 순서 + 제목 + 시간 */}
        <span className={titleCls()}>
          {index}. {title}
        </span>
        <div className="flex flex-row items-center gap-[.625rem]">
          <ClockIcon className="w-[1.1356rem] text-gray-400" />
          <span className="typo-label-1 flex cursor-default items-center">
            {watchedTime}분/{duration}분
          </span>
        </div>
      </div>

      {/* 오른쪽: 업데이트 날짜 */}
      <span className="typo-label-1 cursor-default text-gray-700">
        업데이트 : {formattedDate}
      </span>
    </div>
  );
};

const VideoListItemVariant = tv({
  slots: {
    container:
      "flex items-center justify-between rounded-[.9375rem] border  px-[1.125rem] py-[.9375rem]",
    left: "flex w-[31.25rem] items-center gap-[1.5625rem]",
    playButton:
      "flex h-[3.3125rem] w-[3.3125rem] cursor-pointer items-center justify-center rounded-full border  bg-white p-[.875rem] transition-colors duration-200 ",
    playButtonIcon: "w-[1rem] ",
    title:
      "typo-title-6 w-[15.625rem] cursor-default truncate whitespace-nowrap",
  },
  variants: {
    isCompleted: {
      true: {
        container: "border-main-400 border-2",
        playButton: "hover:bg-main-50 border-main-400 border-2",
        playButtonIcon: "text-main-400",
      },
      false: {
        container: "border-gray-400",
        playButton: "border-gray-400 hover:bg-gray-100",
        playButtonIcon: "text-gray-400",
      },
    },
  },
  defaultVariants: {
    isCompleted: false,
  },
});

export default VideoListItem;
