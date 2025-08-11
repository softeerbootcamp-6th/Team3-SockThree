import TriangleRightIcon from "@/assets/icons/default/triangle-right.svg?react";
import ClockIcon from "@/assets/icons/default/clock.svg?react";
import DeleteIcon from "@/assets/icons/default/delete.svg?react";

interface VideoListItemProps {
  index: number;
  title: string;
  duration: number;
  updatedDate: Date;
}

const VideoListItem = ({
  index,
  title,
  duration,
  updatedDate,
}: VideoListItemProps) => {
  const formattedDate = updatedDate
    .toISOString()
    .split("T")[0]
    .replace(/-/g, ".");

  return (
    <div className="flex w-[72.4375rem] items-center justify-between rounded-[.9375rem] border border-gray-400 px-[1.125rem] py-[.9375rem]">
      {/* 왼쪽: 재생버튼 + 제목 + 시간 */}
      <div className="flex w-[26.875rem] items-center gap-[1.5625rem]">
        {/* 재생 버튼 */}
        <button className="flex h-[3.3125rem] w-[3.3125rem] cursor-pointer items-center justify-center rounded-full border border-gray-400 bg-white p-[.875rem] transition-colors duration-200 hover:bg-gray-100">
          <TriangleRightIcon className="w-[1rem] text-gray-400" />
        </button>

        {/* 순서 + 제목 + 시간 */}
        <span className="typo-title-6 w-[15.625rem] cursor-default truncate whitespace-nowrap">
          {index}. {title}
        </span>
        <div className="flex flex-row items-center gap-[.625rem]">
          <ClockIcon className="w-[1.1356rem] text-gray-400" />
          <span className="typo-label-1 flex cursor-default items-center">
            {duration}분
          </span>
        </div>
      </div>

      {/* 오른쪽: 업데이트 날짜 */}
      <div className="flex items-center justify-end gap-[.625rem]">
        <span className="typo-label-1 cursor-default text-gray-700">
          업데이트 : {formattedDate}
        </span>
        <button className="cursor-pointer">
          <DeleteIcon className="w-[1.4375rem] text-gray-400 hover:text-gray-600" />
        </button>
      </div>
    </div>
  );
};

export default VideoListItem;
