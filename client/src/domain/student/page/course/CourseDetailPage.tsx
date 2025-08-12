import InstructorInfoWidget from "@/domain/student/component/widgets/InstructorInfoWidget";
import ReviewWidget from "@/domain/student/component/widgets/ReviewWidget";

/* 경로: student/course/detail */
const CourseDetailPage = () => {
  return (
    <div>
      <div className="flex h-screen w-full flex-col items-center justify-center gap-[1rem]">
        <div className="flex flex-row gap-[1rem]">
          <ReviewWidget size="small" />
          <InstructorInfoWidget size="small" />
          <InstructorInfoWidget size="small" />
        </div>
        <ReviewWidget size="large" />
      </div>
    </div>
  );
};

export default CourseDetailPage;
