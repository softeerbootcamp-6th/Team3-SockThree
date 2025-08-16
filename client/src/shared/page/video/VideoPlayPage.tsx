import VideoPlayer from "@/shared/components/video/VideoPlayer";
import VideoSidebar from "@/shared/components/video/VideoSideMenu";
import { useState } from "react";
const VideoPlayPage = () => {
  const [activeVideoId, setActiveVideoId] = useState<number>();
  const videoTitle = "OT 및 준비물";
  const videoDescription =
    "이 영상에서는 골프를 시작하기 위한 준비물과 OT 내용을 소개합니다.";

  const videoLists = [
    {
      contentsId: 1,
      contentsTitle: "1. 골프의 기초",
      completedVideos: 1,
      totalVideos: 3,
      videos: [
        {
          id: 101,
          videoTitle: "OT 및 준비물",
          duration: 372, // 6분 12초
          updatedDate: new Date(),
          isCompleted: true,
        },
        {
          id: 102,
          videoTitle: "그립 잡기",
          duration: 765, // 12분 45초
          updatedDate: new Date(),
        },
        {
          id: 103,
          videoTitle: "어드레스 세팅",
          duration: 603,
          updatedDate: new Date(),
        },
      ],
    },
    {
      contentsId: 2,
      contentsTitle: "2. 스윙 만들기",
      completedVideos: 0,
      totalVideos: 2,
      videos: [
        {
          id: 201,
          videoTitle: "백스윙",
          duration: 591,
          updatedDate: new Date(),
        },
        {
          id: 202,
          videoTitle: "다운스윙",
          duration: 680,
          updatedDate: new Date(),
        },
      ],
    },
  ];

  return (
    <div className="flex w-full flex-row">
      <div className="flex flex-col gap-5">
        <h1 className="typo-title-1">{videoTitle}</h1>
        <p className="typo-label-1 w-[62.5rem] text-gray-500">
          {videoDescription}
        </p>

        <VideoPlayer videoUrl="https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8" />
      </div>
      <VideoSidebar
        courseTitle="골프 함께 시작해요"
        startDate={new Date("2023-10-01")}
        endDate={new Date("2023-12-31")}
        completedVideos={1}
        totalVideos={5}
        videoLists={videoLists}
        activeVideoId={activeVideoId}
        onSelectVideo={(id) => {
          setActiveVideoId(id);
        }}
      />
    </div>
  );
};

export default VideoPlayPage;
