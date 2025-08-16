import { Route, Routes, useLocation } from "react-router";
import CourseDetailWrapper from "@/shared/components/course/CourseDetailWrapper";
import CourseDetailDashboardPage from "@/domain/student/page/course/CourseDetailDashboardPage";
import VideoUploadPage from "@/domain/instructor/page/course/VideoUploadPage";

const CourseDetailPage = () => {
  const location = useLocation();
  const isDashboard = location.pathname.includes("/dashboard");

  return (
    <Routes>
      <Route
        element={
          <CourseDetailWrapper
            sidebarContent={
              <div className="w-[20.5rem] bg-white">
                나의 수강생 현황 컴포넌트
              </div>
            }
            isDashboard={isDashboard}
          />
        }
      >
        <Route index element={<div></div>} />
        <Route path="curriculum" element={<VideoUploadPage/>} />
        <Route path="reviews" element={<div></div>} />
        <Route path="dashboard" element={<CourseDetailDashboardPage />} />
        <Route path="assignments" element={<div></div>} />
        <Route path="questions" element={<div></div>} />
      </Route>
    </Routes>
  );
};

export default CourseDetailPage;
