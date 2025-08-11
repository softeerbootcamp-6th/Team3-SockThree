import CourseInfoCard from "@/shared/components/course/CourseInfoCard";
import { CourseInfoCardDefault } from "@/domain/student/component/course/CourseInfoCardDefault";
import { Outlet, NavLink, useParams } from "react-router-dom";

const CourseDetailLayout = () => {
  const { courseId } = useParams(); // 이 id를 통해 렌더링 불러옴
  const isEnrolled = false;
  console.log(courseId);
  const baseTabs = [
    { to: "curriculum", label: "커리큘럼" },
    { to: "reviews", label: "리뷰" },
  ];
  const extraTabs = [
    { to: "dashboard", label: "대시보드", gated: true },
    { to: "assignments", label: "과제" },
    { to: "questions", label: "질문" },
  ];

  const tabs = [
    ...baseTabs,
    ...(isEnrolled ? extraTabs : extraTabs.filter((t) => !t.gated)),
  ];

  return (
    <main className="mx-auto w-full max-w-screen-2xl px-4 sm:px-6 lg:px-8">
      {/* 메인/사이드 2열 그리드 */}
      <div className="grid grid-cols-1 gap-[1rem] py-6 lg:grid-cols-12 lg:gap-[1rem]">
        {/* 메인 영역 */}
        <section className="flex min-h-[50vh] flex-col gap-[1rem] lg:col-span-8 xl:col-span-9">
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
          <div className="min-h-[10rem] w-full rounded-[1.5rem] bg-white shadow-bg">
            <div className="flex flex-col gap-[0.5rem] p-[2rem]">
              <h3 className="typo-body-4">강좌 소개</h3>
              <span className="line-clamp-5 max-h-[5rem] min-h-[4rem]">
                description
              </span>
            </div>
          </div>
          <nav className="sticky top-[5rem] z-100 border-b border-gray-200 bg-white/80 backdrop-blur">
            <div className="mx-auto max-w-screen-2xl px-4 sm:px-6 lg:px-8">
              <div role="tablist" className="flex h-12 items-center gap-6">
                {tabs.map(({ to, label }) => (
                  <NavLink
                    key={to}
                    to={to}
                    end
                    className={({ isActive }) =>
                      [
                        "inline-flex h-12 items-center border-b-2 text-sm font-medium",
                        isActive
                          ? "border-gray-900 text-gray-900"
                          : "border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-900",
                      ].join(" ")
                    }
                  >
                    {label}
                  </NavLink>
                ))}
              </div>
            </div>
          </nav>
          <div className="flex-1">
            <Outlet />
          </div>
        </section>

        {/* 사이드바 영역 */}
        <aside className="lg:col-span-4 xl:col-span-3">
          {/* 사이드바 카드 자체를 sticky 처리.
                top 오프셋은 헤더 높이와 동일하게 맞춤(h-16 => top-16) */}
          <div className="sticky top-[6rem]">
            <CourseInfoCardDefault status="recruiting" />
          </div>
        </aside>
      </div>
    </main>
  );
};

export default CourseDetailLayout;
