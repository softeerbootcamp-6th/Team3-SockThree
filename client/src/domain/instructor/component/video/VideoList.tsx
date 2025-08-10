import { useState } from "react";
import VideoListItem from "@/domain/instructor/component/video/VideoListItem";

import TriangleUpIcon from "@/assets/icons/default/triangle-up.svg?react";
import TriangleDownIcon from "@/assets/icons/default/triangle-down.svg?react";
import EditIcon from "@/assets/icons/default/edit.svg?react";
import DeleteIcon from "@/assets/icons/default/delete.svg?react";

interface VideoData {
  id: number;
  videoTitle: string;
  duration: number;
  updatedDate: Date;
}

interface VideoListProps {
  contentsTitle: string; // 예: "골프의 기초"
  videos: VideoData[];
}

const VideoList = ({ contentsTitle, videos }: VideoListProps) => {
  const [isOpen, setIsOpen] = useState(false); // 토글 상태

  return (
    <div className="flex w-[74.875rem] flex-col gap-4 rounded-xl bg-white px-[1.625rem] py-[1.5rem] transition-all duration-300 ease-in-out">
      {/* 헤더 */}
      <div className="flex items-center justify-between">
        <div className="flex flex-col">
          <h2 className="typo-body-4">{contentsTitle}</h2>
          <div className="flex flex-row items-center gap-[.6875rem]">
            <p className="typo-label-0 py-[.625rem] text-gray-600">
              총 강의 수 <span className="text-main-500">{videos.length}</span>{" "}
              개
            </p>
            <button
              onClick={() => setIsOpen((prev) => !prev)}
              className="cursor-pointer"
            >
              {isOpen ? (
                <TriangleUpIcon className="w-[1.0625rem] text-gray-500" />
              ) : (
                <TriangleDownIcon className="w-[1.0625rem] text-gray-500" />
              )}
            </button>
          </div>
        </div>

        {/* 토글 버튼 */}
        <div className="flex items-center gap-[3.625rem]">
          <button>
            <EditIcon className="w-[1.4375rem] cursor-pointer text-gray-600" />
          </button>
          <button>
            <DeleteIcon className="w-[1.4375rem] cursor-pointer text-gray-600" />
          </button>
        </div>
      </div>

      {/* VideoListItem 목록 (isOpen일 때만 표시) */}
      {isOpen && (
        <div className="flex flex-col gap-[.75rem] pt-[1.125rem] pb-[.75rem]">
          {videos.map((video, idx) => (
            <VideoListItem
              key={video.id}
              index={idx + 1}
              title={video.videoTitle}
              duration={video.duration}
              updatedDate={video.updatedDate}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default VideoList;
