import { useState } from "react";
import VideoListItem from "@/domain/student/component/video/VideoListItem";

import TriangleUpIcon from "@/assets/icons/default/triangle-up.svg?react";
import TriangleDownIcon from "@/assets/icons/default/triangle-down.svg?react";
import ProgressBar from "@/shared/components/ProgressBar";

import type { VideoListData } from "@/domain/student/types/video";

interface VideoListProps {
  content: VideoListData;
}

const VideoList = ({ content }: VideoListProps) => {
  const [isListOpen, setIsListOpen] = useState(true);

  const {
    contentsId = 1,
    contentsTitle = "목차명을 입력하세요.",
    completedVideos = 0,
    totalVideos = 0,
    videos = [],
  } = content;

  return (
    <div
      id={`${contentsId}`}
      className="flex w-[74.875rem] flex-col gap-4 rounded-xl bg-white px-[1.625rem] py-[1.5rem] transition-all duration-300 ease-in-out"
    >
      {/* 헤더 */}
      <div className="flex items-center justify-between">
        <div className="flex flex-col">
          <h2 className="typo-body-4">{contentsTitle}</h2>
          <div className="flex flex-row items-center gap-[.6875rem]">
            <p className="typo-label-0 py-[.625rem] text-gray-600">
              총 강의 수 <span className="text-main-500">{videos.length}</span>{" "}
              개
            </p>

            {/* 토글 버튼 */}
            <button
              onClick={() => setIsListOpen((prev) => !prev)}
              className="cursor-pointer"
            >
              {isListOpen ? (
                <TriangleUpIcon className="w-[1.0625rem] text-gray-500" />
              ) : (
                <TriangleDownIcon className="w-[1.0625rem] text-gray-500" />
              )}
            </button>
          </div>
        </div>
        <ProgressBar
          size="xs"
          type="smallRatio"
          current={completedVideos}
          max={totalVideos}
        />
      </div>

      {/* VideoListItem 목록 (isListOpen일 때만 표시) */}
      {isListOpen && (
        <div className="flex flex-col gap-[.75rem] pt-[1.125rem] pb-[.75rem]">
          {videos.map((video, idx) => (
            <VideoListItem key={video.id} index={idx + 1} video={video} />
          ))}
        </div>
      )}
    </div>
  );
};

export default VideoList;
