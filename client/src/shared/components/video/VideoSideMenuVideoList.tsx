import type { VideoData } from "@/domain/student/types/video";
import { formatTime } from "@/shared/utils/timeUtils";

interface VideoSideMenuVideoListProps {
  videos: VideoData[];
  activeVideoId?: number;
  onSelectVideo?: (videoId: number) => void;
  isMenuOpen: boolean;
}

const VideoSideMenuVideoList = ({
  videos,
  activeVideoId,
  onSelectVideo,
  isMenuOpen,
}: VideoSideMenuVideoListProps) => {
  return (
    <ul className="flex flex-col">
      {videos.map((video) => {
        const active = video.id === activeVideoId;
        return (
          <li key={video.id}>
            {isMenuOpen && (
              <button
                onClick={() => onSelectVideo?.(video.id)}
                className={`typo-label-1 flex w-full items-center gap-3 bg-white p-5 text-left text-black ${
                  active ? "bg-main-50 text-main-500" : "hover:bg-gray-50"
                }`}
              >
                <span
                  className={`h-2 w-2 rounded-full ${
                    video.isCompleted ? "bg-main-500" : "bg-gray-300"
                  }`}
                />
                <span className="flex-1 truncate">
                  {video.videoTitle}
                </span>
                <span className="typo-label-4 text-gray-500">
                  {formatTime(video.duration)}
                </span>
              </button>
            )}
          </li>
        );
      })}
    </ul>
  );
};

export default VideoSideMenuVideoList;
