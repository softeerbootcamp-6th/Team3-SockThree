import { useLocation, Routes, Route } from "react-router";
import CourseInfoCardAttend from "@/domain/student/component/course/CourseInfoCardAttend";
import CourseDetailWrapper from "@/shared/components/course/CourseDetailWrapper";
import CourseInfoCardDefault from "@/domain/student/component/course/CourseInfoCardDefault";

const CourseDetailPage = () => {
  const location = useLocation();
  const isDashboard = location.pathname.includes("/dashboard");

  const rightSideBarComponent = isDashboard ? (
    <CourseInfoCardAttend />
  ) : (
    <CourseInfoCardDefault status="recruiting" />
  );

  return (
    <CourseDetailWrapper
      isDashboard={isDashboard}
      sidebarContent={rightSideBarComponent}
    >
      {/*  탭 내부 라우팅 */}
      <Routes>
        <Route index element={<div></div>} />
        <Route path="curriculum" element={<div></div>} />
        <Route path="reviews" element={<div></div>} />
        <Route path="dashboard" element={<div></div>} />
        <Route path="assignments" element={<div></div>} />
        <Route path="questions" element={<div></div>} />
      </Routes>
    </CourseDetailWrapper>
  );
};

export default CourseDetailPage;
