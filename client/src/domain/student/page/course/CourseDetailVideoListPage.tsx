import VideoList from "@/domain/student/component/video/VideoList";

const CourseDetailVideoListPage = () => {
  return (
    <div className="flex h-full flex-col items-center justify-center gap-3">
      <VideoList
        contentsId={1}
        contentsTitle="싹쓰리 골프의 시작"
        completedVideos={1}
        totalVideos={2}
        videos={[
          {
            id: 1,
            videoTitle: "골프의 기초",
            duration: 36,
            isCompleted: true,
            watchedTime: 36,
            updatedDate: new Date(),
          },
          {
            id: 2,
            videoTitle: "골프의 기본기",
            duration: 180,
            updatedDate: new Date(),
          },
        ]}
      />
      <VideoList
        contentsId={2}
        contentsTitle="추가 강의 목록"
        completedVideos={0}
        totalVideos={2}
        videos={[
          {
            id: 3,
            videoTitle: "골프의 심화",
            duration: 240,
            updatedDate: new Date(),
          },
          {
            id: 4,
            videoTitle: "골프 마스터",
            duration: 300,
            updatedDate: new Date(),
          },
        ]}
      />
    </div>
  );
};

export default CourseDetailVideoListPage;
