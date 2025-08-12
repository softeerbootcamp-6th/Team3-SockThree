import { NavLink, useParams } from "react-router";

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

  let tabs: typeof baseTabs;

  if (isEnrolled) {
    const dashboardTab = extraTabs.find((t) => t.to === "dashboard")!;
    const otherExtraTabs = extraTabs.filter((t) => t.to !== "dashboard");
    tabs = [dashboardTab, ...baseTabs, ...otherExtraTabs];
  } else {
    tabs = [...baseTabs, ...extraTabs.filter((t) => !t.gated)];
  }
  
  const { courseId } = useParams<{ courseId: string }>();

  const makeAbsolutePath = (to: string) => {
    return `/student/course/${courseId}/${to}`;
  }

  return (
    <nav className="sticky top-[5rem] z-100 w-full bg-bg py-[1rem] backdrop-blur">
      <div className="mx-auto max-w-screen-2xl border-b border-gray-300 px-4 sm:px-6 lg:px-8">
        <div role="tablist" className="flex h-16 items-end gap-8">
          {tabs.map(({ to, label }) => (
            <NavLink
              key={to}
              to={makeAbsolutePath(to)}
              end
              className={({ isActive }) =>
                [
                  "-mb-px inline-flex h-full items-center border-b-3 px-5 py-3 transition-colors",
                  isActive
                    ? "border-main-400 text-gray-900"
                    : "border-transparent text-gray-500 hover:text-gray-900",
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
