import CourseInfoCard from "@/shared/components/course/CourseInfoCard";

const CourseDetailPage = () => {
  return (
    <div className="flex h-full w-full flex-col overflow-y-auto pt-[4rem] pr-[3.5625rem] pb-[3.75rem] pl-[2.8125rem]">
      <CourseInfoCard
        courseInfo={{
          id: 1,
          title: "골프 기초부터 마스터하기",
          description: "골프의 기초부터 고급 기술까지 배울 수 있는 종합 과정입니다.",
          heartCount: 100,
          tags: ["운동", "골프", "월/수/금", "D-7"],
          teacherName: "강사 이름",
          nowStudents: 10,
          maxStudents: 30,
          courseImg:
            "https://t4.ftcdn.net/jpg/02/42/33/71/360_F_242337142_2rtf0nXrG4N1MGs8gKGswtg5si1TfPEX.jpg",
        }}
      />
    </div>
  );
};

export default CourseDetailPage;
