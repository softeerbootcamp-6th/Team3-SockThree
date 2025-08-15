import CourseInfoCard from "@/shared/components/course/CourseInfoCard";
import type { Course } from "@/shared/components/course/CourseInfoCard";

interface CourseDetailHeaderProps {
  courseInfo: Course;
  description?: string;
}

const CourseDetailHeader = ({
  courseInfo,
  description = "",
}: CourseDetailHeaderProps) => {
  return (
    <>
      <CourseInfoCard courseInfo={courseInfo} />
      <div className="min-h-[10rem] w-full rounded-[1.5rem] bg-white shadow-bg">
        <div className="flex flex-col gap-[0.5rem] p-[2rem]">
          <h3 className="typo-body-4">강좌 소개</h3>
          <span className="line-clamp-5 max-h-[5rem] min-h-[4rem]">
            {description}
          </span>
        </div>
      </div>
    </>
  );
};

export default CourseDetailHeader;
