import CourseDetailTabBar from "@/shared/components/course/CourseDetailTabBar";
import CourseDetailHeader from "@/shared/components/course/CourseDetailHeader";

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
  if (isDashboard) {
    return (
      <>
        <div className="flex w-full flex-row items-start justify-between gap-[1rem] pt-[1.5rem]">
          <section className="mb-[1rem] flex w-full flex-col items-start justify-start gap-[1rem]">
            <CourseDetailHeader courseInfo={courseInfo} />
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
          <CourseDetailHeader courseInfo={courseInfo} />
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
