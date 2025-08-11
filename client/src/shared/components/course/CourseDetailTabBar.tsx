import { NavLink } from "react-router-dom";

interface CourseDetailTabBarProps {
  isEnrolled: boolean;
}

const CourseDetailTabBar = ({
  isEnrolled = false,
}: CourseDetailTabBarProps) => {
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
    <nav className="sticky top-[5rem] z-100 bg-bg backdrop-blur">
      {/* 전체 폭 하단 라인 + 수평 패딩 */}
      <div className="mx-auto max-w-screen-2xl border-b border-gray-300 px-4 sm:px-6 lg:px-8">
        <div role="tablist" className="flex h-16 items-end gap-8">
          {tabs.map(({ to, label }) => (
            <NavLink
              key={to}
              to={to}
              end
              className={({ isActive }) =>
                [
                  // 높이와 패딩을 넉넉하게
                  "-mb-px inline-flex h-full items-center border-b-2 px-5 py-3 transition-colors",
                  isActive
                    ? "border-main-600 text-gray-900"
                    : "border-transparent text-gray-500 hover:border-gray-400 hover:text-gray-900",
                ].join(" ")
              }
            >
              <span className="bg-transparent pb-[0.3rem]">{label}</span>
            </NavLink>
          ))}
        </div>
      </div>
    </nav>
  );
};

export default CourseDetailTabBar;
