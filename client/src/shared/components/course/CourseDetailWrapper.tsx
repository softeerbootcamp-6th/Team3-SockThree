import CourseInfoCard from "@/shared/components/course/CourseInfoCard";
import CourseDetailTabBar from "@/shared/components/course/CourseDetailTabBar";

interface CourseDetailWrapperProps {
  children: React.ReactNode;
  sidebarContent?: React.ReactNode;
  isDashboard?: boolean;
}

const CourseDetailWrapper = ({
  children,
  sidebarContent,
  isDashboard = false,
}: CourseDetailWrapperProps) => {
  const headerSection = (
    <>
      <CourseInfoCard courseInfo={courseInfo} />
      <div className="min-h-[10rem] w-full rounded-[1.5rem] bg-white shadow-bg">
        <div className="flex flex-col gap-[0.5rem] p-[2rem]">
          <h3 className="typo-body-4">강좌 소개</h3>
          <span className="line-clamp-5 max-h-[5rem] min-h-[4rem]">
            {courseInfo.description}
          </span>
        </div>
      </div>
    </>
  );

  if (isDashboard) {
    return (
      <>
        <div className="flex w-full flex-row items-start justify-between gap-[1rem] pt-[1.5rem]">
          <section className="mb-[1rem] flex w-full flex-col items-start justify-start gap-[1rem]">
            {headerSection}
          </section>
          {sidebarContent && <div>{sidebarContent}</div>}
        </div>
        <CourseDetailTabBar isEnrolled={true} />
        <div className="mt-[1rem] w-full">{children}</div>
      </>
    );
  }

  return (
    <>
      <div className="flex w-full flex-row gap-[1rem] py-6">
        <section className="flex flex-1 flex-col gap-[1rem]">
          {headerSection}
          <CourseDetailTabBar isEnrolled={true} />
          <div className="flex-1">{children}</div>
        </section>
        {sidebarContent && (
          <aside>
            <div className="sticky top-[6rem]">{sidebarContent}</div>
          </aside>
        )}
      </div>
    </>
  );
};

export default CourseDetailWrapper;

const MainContainer = ({ children }: { children: React.ReactNode }) => {
  return (
    <main className="mx-auto flex max-w-[1720px] min-w-[1280px] flex-col items-center justify-center">
      {children}
    </main>
  );
};

const courseInfo = {
  id: 1,
  title: "프론트엔드 개발자 양성과정",
  description: "이 과정은 프론트엔드 개발의 기초부터 심화까지 다룹니다.",
  heartCount: 129,
  tags: ["프론트엔드", "웹 개발", "JavaScript"],
  teacherName: "홍길동",
  nowStudents: 30,
  maxStudents: 30,
  courseImg: "",
};
