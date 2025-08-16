import { Route, Routes, useLocation } from "react-router";
import CourseDetailWrapper from "@/shared/components/course/CourseDetailWrapper";
import CourseDetailDashboardPage from "@/domain/student/page/course/CourseDetailDashboardPage";

const CourseDetailPage = () => {
  const location = useLocation();
  const isDashboard = location.pathname.includes("/dashboard");

  return (
    <CourseDetailWrapper
      sidebarContent={
        <div className="w-[20.5rem] bg-white">나의 수강생 현황 컴포넌트</div>
      }
      isDashboard={isDashboard}
    >
      {/*  탭 내부 라우팅 */}
      <Routes>
        <Route index element={<div></div>} />
        <Route path="curriculum" element={<div>비디오</div>} />
        <Route path="reviews" element={<div></div>} />
        <Route path="dashboard" element={<CourseDetailDashboardPage />} />
        <Route path="assignments" element={<div></div>} />
        <Route path="questions" element={<div></div>} />
      </Routes>
    </CourseDetailWrapper>
  );
};

export default CourseDetailPage;
