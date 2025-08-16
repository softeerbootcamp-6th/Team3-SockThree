import VideoList from "@/domain/student/component/video/VideoList";

const CourseDetailVideoListPage = () => {
  const videoListData = [
    {
      contentsId: 1,
      contentsTitle: "강의 목차 1",
      videos: [
        {
          id: 1,
          videoTitle: "강의 1",
          duration: 300,
          updatedDate: new Date(),
          watchedTime: 150,
          isCompleted: true,
        },
        {
          id: 2,
          videoTitle: "강의 2",
          duration: 600,
          updatedDate: new Date(),
        },
      ],
      completedVideos: 1,
      totalVideos: 2,
    },
    {
      contentsId: 2,
      contentsTitle: "강의 목차 2",
      videos: [
        {
          id: 3,
          videoTitle: "강의 3",
          duration: 450,
          updatedDate: new Date(),
        },
      ],
      completedVideos: 0,
      totalVideos: 1,
    },
  ];

  let globalVideoIndex = 0;

  return (
    <div className="flex w-full flex-col gap-[1.5rem]">
      {videoListData.map((content) => {
        const startIndex = globalVideoIndex + 1;
        globalVideoIndex += content.videos.length;
        return (
          <VideoList
            key={content.contentsId}
            content={content}
            startIndex={startIndex}
          />
        );
      })}
    </div>
  );
};

export default CourseDetailVideoListPage;
