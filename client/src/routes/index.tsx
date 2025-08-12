import StudentLayout from "@/shared/layout/StudentLayout";
import InstructorLayout from "@/shared/layout/InstructorLayout";
import StickyLogoLayout from "@/shared/layout/StickyLogoLayout";
import CourseDetailLayout from "@/shared/layout/CourseDetailLayout";

import * as S from "@/domain/student/page";
import * as I from "@/domain/instructor/page";

import { Route, Routes } from "react-router";
import { Navigate } from "react-router-dom";
import CourseDashboardLayout from "@/shared/layout/CourseDashboardLayout";

// HyFive 팀 코드 참고했습니다

const Router = () => {
  return (
    <Routes>
      <Route path="/student">
        <Route path="course">
          <Route element={<StickyLogoLayout />}>
            <Route path="detail/:courseId">
              {/* 대시보드 전용 레이아웃 */}
              <Route element={<CourseDashboardLayout />}>
                <Route
                  path="dashboard"
                  element={<S.CourseDetailDashboardPage />}
                />
              </Route>
              {/* 일반 탭 레이아웃 */}
              <Route element={<CourseDetailLayout />}>
                <Route index element={<Navigate to="curriculum" replace />} />
                <Route
                  path="curriculum"
                  element={
                    <div className="h-[100vh] bg-amber-200">커리큘럼</div>
                  }
                />
                <Route path="reviews" element={<div>리뷰</div>} />
                {/* 선택 탭들 */}

                <Route path="assignments" element={<div>과제</div>} />
                <Route path="questions" element={<div>질문</div>} />
              </Route>
            </Route>
          </Route>
        </Route>

        <Route element={<StudentLayout />}>
          <Route path="home" element={<S.HomePage />} />

          <Route path="mypage">
            <Route path="" element={<S.MyPage />} />
            <Route path="heart" element={<S.HeartPage />} />
            <Route path="my-course" element={<S.MyCoursePage />} />
          </Route>

          <Route path="course">
            <Route path="search" element={<S.SearchResultPage />} />
          </Route>
        </Route>
      </Route>

      {/* 강사 */}
      <Route path="/instructor">
        <Route element={<InstructorLayout />}>
          <Route path="home" element={<I.HomePage />} />
          <Route path="mypage" element={<I.MyPage />} />

          <Route path="course">
            <Route path="manage" element={<I.ManageCoursePage />} />
            <Route path="detail" element={<I.CourseDetailPage />} />
          </Route>
        </Route>
        <Route path="course">
          <Route path="create" element={<I.CreateCoursePage />} />
        </Route>
      </Route>

      {/* 초기 진입 시 학생 페이지 기본 - 추후 수정 */}
      <Route path="/" element={<StudentLayout />}>
        <Route index element={<S.HomePage />} />
      </Route>
    </Routes>
  );
};

export default Router;
