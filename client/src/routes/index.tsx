import StudentLayout from "@/domain/student/layout/StudentLayout";
import InstructorLayout from "@/domain/instructor/layout/InstructorLayout";
import NoNavbarLayout from "@/shared/layout/NoNavbarLayout";

import * as S from "@/domain/student/page";
import * as I from "@/domain/instructor/page";

import { Route, Routes } from "react-router";

// HyFive 팀 코드 참고했습니다

const Router = () => {
  return (
    <Routes>
      {/* 시니어 (학생) */}
      <Route path="/student">
        <Route element={<NoNavbarLayout />}>
          <Route path="course">
            <Route path="detail" element={<S.CourseDetailPage />} />
          </Route>
        </Route>

        <Route element={<StudentLayout />}>
          <Route path="home" element={<S.HomePage />} />

          <Route path="mypage">
            <Route path="" element={<S.HomePage />} />
            <Route path="heart" element={<S.SearchResultPage />} />
          </Route>

          <Route path="course">
            <Route path="search" element={<S.SearchResultPage />} />
            <Route path="course" element={<S.HomePage />} />
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

        <Route element={<NoNavbarLayout />}>
          <Route path="course">
            <Route path="create" element={<I.CreateCoursePage />} />
          </Route>
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
