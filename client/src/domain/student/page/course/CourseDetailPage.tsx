import InstructorInfoWidget from "@/domain/student/component/widgets/InstructorInfoWidget";

/* 경로: student/course/detail */
const CourseDetailPage = () => {
  return (
    <div>
      <div className="flex h-screen w-full flex-col items-center justify-center gap-[1rem]">
        <div className="flex flex-row gap-[1rem]">
          <InstructorInfoWidget size="small" />
          <InstructorInfoWidget size="small" />
          <InstructorInfoWidget size="small" />
        </div>
        <InstructorInfoWidget size="large" />
      </div>
    </div>
  );
};

export default CourseDetailPage;
