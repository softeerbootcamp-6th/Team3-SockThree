import { Link, useLocation, useParams } from "react-router";

interface CourseDetailTabBarProps {
  isEnrolled: boolean;
}

const CourseDetailTabBar = ({
  isEnrolled = false,
}: CourseDetailTabBarProps) => {
  const { courseId } = useParams();
  const location = useLocation();

  const baseTabs = [
    { path: "", label: "개요" }, // 기본 경로 추가
    { path: "/curriculum", label: "커리큘럼" },
    { path: "/reviews", label: "리뷰" },
  ];

  const extraTabs = [
    { path: "/dashboard", label: "대시보드", gated: true },
    { path: "/assignments", label: "과제" },
    { path: "/questions", label: "질문" },
  ];

  let tabs: typeof baseTabs;

  if (isEnrolled) {
    const dashboardTab = extraTabs.find((t) => t.path === "/dashboard")!;
    const otherExtraTabs = extraTabs.filter((t) => t.path !== "/dashboard");
    tabs = [dashboardTab, ...baseTabs, ...otherExtraTabs];
  } else {
    tabs = [...baseTabs, ...extraTabs.filter((t) => !t.gated)];
  }

  const isActiveTab = (tabPath: string) => {
    const currentPath = location.pathname;
    const baseCoursePath = `/course/${courseId}`;

    if (tabPath === "") {
      return currentPath === baseCoursePath;
    }
    return currentPath === `${baseCoursePath}${tabPath}`;
  };

  return (
    <nav className="sticky top-[5rem] z-100 w-full bg-bg py-[1rem] backdrop-blur">
      <div className="mx-auto max-w-screen-2xl border-b border-gray-300 px-4 sm:px-6 lg:px-8">
        <div role="tablist" className="flex h-16 items-end gap-8">
          {tabs.map(({ path, label }) => (
            <Link
              key={path}
              to={`/course/${courseId}${path}`}
              className={[
                "-mb-px inline-flex h-full items-center border-b-3 px-5 py-3 transition-colors",
                isActiveTab(path)
                  ? "border-main-400 text-gray-900"
                  : "border-transparent text-gray-500 hover:text-gray-900",
              ].join(" ")}
            >
              <span className="bg-transparent pb-[0.3rem]">{label}</span>
            </Link>
          ))}
        </div>
      </div>
    </nav>
  );
};

export default CourseDetailTabBar;
