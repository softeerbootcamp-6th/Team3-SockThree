import { useState } from "react";
import type { VideoListData } from "@/domain/student/types/video";

import ArrowRightIcon from "@/assets/icons/default/arrow-right.svg?react";
import MenuIcon from "@/assets/icons/default/menu.svg?react";
import ProgressBar from "@/shared/components/ProgressBar";
import VideoSideMenuItem from "@/shared/components/video/VideoSideMenuItem";

import { formatDate } from "@/shared/utils/dateUtils";

interface VideoSidebarProps {
  courseTitle: string;
  startDate: Date;
  endDate: Date;
  completedVideos?: number;
  totalVideos?: number;
  videoLists: VideoListData[];
  activeVideoId?: number;
  isProgress?: boolean;
  onSelectVideo?: (videoId: number) => void;
}

const VideoSidebar = ({
  courseTitle,
  startDate,
  endDate,
  completedVideos = 0,
  totalVideos = 0,
  videoLists,
  activeVideoId,
  isProgress = true,
  onSelectVideo,
}: VideoSidebarProps) => {
  const [isOpen, setIsOpen] = useState(true);

  const handelMenuClick = () => {
    setIsOpen(!isOpen);
  };

  return (
    <aside
      className={`absolute right-0 h-screen flex-col overflow-y-auto bg-bg transition-all duration-300 ${
        isOpen ? "w-[450px] shadow-main" : "w-[72px]"
      }`}
    >
      {/* 헤더 */}
      <div className="flex items-start gap-3 p-5">
        <button onClick={handelMenuClick} className="cursor-pointer p-2">
          {isOpen ? (
            <ArrowRightIcon className="w-[.8125rem] text-gray-600" />
          ) : (
            <MenuIcon className="w-[1.25rem] text-gray-600" />
          )}
        </button>
        {isOpen && (
          <div className="flex flex-col gap-3">
            <span className="typo-title-3 truncate">{courseTitle}</span>
            <p className="typo-label-1 text-gray-500">
              {formatDate(startDate, ".", true)}~
              {formatDate(endDate, ".", true)}
            </p>

            {isProgress && (
              <div className="typo-label-1 flex w-full items-center gap-3 text-gray-500">
                <span>수강률</span>

                <ProgressBar
                  size="sm"
                  type="smallPercent"
                  current={completedVideos}
                  max={totalVideos}
                />
              </div>
            )}
          </div>
        )}
      </div>

      {/* 챕터/비디오 목록 */}
      <VideoSideMenuItem
        videoLists={videoLists}
        activeVideoId={activeVideoId}
        onSelectVideo={onSelectVideo}
        isMenuOpen={isOpen}
      />
    </aside>
  );
};

export default VideoSidebar;
