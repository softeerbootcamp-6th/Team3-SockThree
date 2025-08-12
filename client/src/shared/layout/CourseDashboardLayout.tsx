// CourseDashboardLayout.tsx  (대시보드: 상단 사이드바 + 메인 전체폭)
import { Outlet, useParams } from "react-router";
import CourseInfoCard from "@/shared/components/course/CourseInfoCard";
import CourseDetailTabBar from "@/shared/components/course/CourseDetailTabBar";
import CourseInfoCardAttend from "@/domain/student/component/course/CourseInfoCardAttend";

const CourseDashboardLayout = () => {
  const { courseId } = useParams(); // 이 id를 통해 렌더링 불러옴
  console.log(courseId);

  return (
    <main className="mx-auto flex max-w-[1720px] min-w-[1280px] flex-col items-center justify-center">
      {/* 전체 flex 구조 */}
      <div className="flex w-full flex-row items-start justify-between gap-[1rem] pt-[1.5rem]">
        {/* 상단 카드 & 설명 영역 */}
        <section className="flex w-full flex-col items-start justify-start gap-[1rem]">
          <CourseInfoCard
            courseInfo={{
              id: 1,
              title: "프론트엔드 개발자 양성과정",
              description:
                "이 과정은 프론트엔드 개발의 기초부터 심화까지 다룹니다.",
              heartCount: 129,
              tags: ["프론트엔드", "웹 개발", "JavaScript"],
              teacherName: "홍길동",
              nowStudents: 30,
              maxStudents: 30,
              courseImg: "",
            }}
          />
          {/*강좌 설명 박스 */}
          <div className="mb-[1rem] min-h-[10rem] w-full rounded-[1.5rem] bg-white shadow-bg">
            <div className="flex flex-col gap-[0.5rem] p-[2rem]">
              <h3 className="typo-body-4">강좌 소개</h3>
              <span className="line-clamp-5 max-h-[5rem] min-h-[4rem]">
                description
              </span>
            </div>
          </div>
        </section>
        {/* 사이드바 */}
        <div>
          <CourseInfoCardAttend />
        </div>
      </div>

      <CourseDetailTabBar isEnrolled={true} />
      <div className="mt-[1rem] w-full">
        <Outlet />
      </div>
    </main>
  );
};

export default CourseDashboardLayout;
