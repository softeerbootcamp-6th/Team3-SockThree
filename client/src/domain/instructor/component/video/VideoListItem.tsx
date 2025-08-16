import TriangleRightIcon from "@/assets/icons/default/triangle-right.svg?react";
import ClockIcon from "@/assets/icons/default/clock.svg?react";
import DeleteIcon from "@/assets/icons/default/delete.svg?react";

import { formatDate } from "@/shared/utils/dateUtils";
import ProgressBar from "@/shared/components/ProgressBar";

interface VideoListItemProps {
  index: number;
  title: string;
  duration: number;
  uploadDate: Date;
  releaseDate: Date;
  isUploading: boolean;
  uploadProgress?: number;
}

const VideoListItem = ({
  index,
  title,
  duration,
  uploadDate,
  releaseDate,
  isUploading,
  uploadProgress,
}: VideoListItemProps) => {
  const formattedUploadDate = formatDate(uploadDate, ".", true);
  const formattedReleaseDate = formatDate(releaseDate, ".", true);

  return (
    <div className="flex w-[72.4375rem] items-center justify-between rounded-[.9375rem] border border-gray-400 px-[1.125rem] py-[.9375rem]">
      {/* 왼쪽: 재생버튼 + 제목 + 시간 */}
      <div className="flex w-[26.875rem] items-center gap-[1.5625rem]">
        {/* 재생 버튼 */}
        <button
          className={`flex h-[3.3125rem] w-[3.3125rem] items-center justify-center rounded-full border border-gray-400 bg-white p-[.875rem] transition-colors duration-200 ${isUploading ? "opacity-50" : "cursor-pointer hover:bg-gray-100"} `}
          disabled={isUploading}
        >
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
      <div className="flex items-center justify-end gap-[.9375rem]">
        {/* 업로드 중일 때 : 진행률 + 업로드 중 텍스트 */}
        {isUploading && (
          <>
            <span className="typo-label-1 text-gray-500">업로드 중</span>
            <ProgressBar
              value={uploadProgress || 0}
              type="smallPercent"
              size="sm"
            />
          </>
        )}

        {/* 업로드 완료 시: 업로드 날짜 + 공개 일정 + 삭제 버튼 */}
        {!isUploading && (
          <>
            <span className="typo-label-1 cursor-default text-gray-700">
              업로드 : {formattedUploadDate}
            </span>
            <span className="typo-label-1 cursor-default text-gray-700">
              공개 일정 : {formattedReleaseDate}
            </span>
            <button className="cursor-pointer">
              <DeleteIcon className="w-[1.4375rem] text-gray-400 hover:text-gray-600" />
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default VideoListItem;
