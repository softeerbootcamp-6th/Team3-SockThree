import { useState } from "react";
import type { VideoListData } from "@/domain/student/types/video";
import VideoSideMenuChapterHeader from "@/shared/components/video/VideoSideMenuChapterHeader";
import VideoSideMenuVideoList from "@/shared/components/video/VideoSideMenuVideoList";

interface VideoSideMenuItemProps {
  videoLists: VideoListData[];
  activeVideoId?: number;
  onSelectVideo?: (videoId: number) => void;
  isMenuOpen: boolean;
}

const VideoSideMenuItem = ({
  videoLists,
  activeVideoId,
  onSelectVideo,
  isMenuOpen,
}: VideoSideMenuItemProps) => {
  const [openMap, setOpenMap] = useState<Record<number, boolean>>(() =>
    videoLists.reduce(
      (acc, list) => {
        if (list.contentsId != null) acc[list.contentsId] = true;
        return acc;
      },
      {} as Record<number, boolean>
    )
  );

  const toggleChapter = (id: number) => {
    setOpenMap((prev) => ({ ...prev, [id]: !prev[id] }));
  };

  return (
    <nav>
      <ul>
        {videoLists.map((list) => {
          const id = list.contentsId ?? 0;
          const open = openMap[id];

          return (
            <li key={id}>
              {/* 챕터 헤더 */}
              <VideoSideMenuChapterHeader
                id={id}
                title={list.contentsTitle}
                isOpen={open}
                onToggle={toggleChapter}
                isMenuOpen={isMenuOpen}
              />

              {/* 비디오 목록 */}
              {open && list.videos && (
                <VideoSideMenuVideoList
                  videos={list.videos}
                  activeVideoId={activeVideoId}
                  onSelectVideo={onSelectVideo}
                  isMenuOpen={isMenuOpen}
                />
              )}
            </li>
          );
        })}
      </ul>
    </nav>
  );
};

export default VideoSideMenuItem;
