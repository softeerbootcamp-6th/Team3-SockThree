import StudentLayout from "@/shared/layout/StudentLayout";
import InstructorLayout from "@/shared/layout/InstructorLayout";
import StickyLogoLayout from "@/shared/layout/StickyLogoLayout";
import CourseDetailLayout from "@/shared/layout/CourseDetailLayout";
import CourseDashboardLayout from "@/shared/layout/CourseDashboardLayout";

import * as S from "@/domain/student/page";
import * as I from "@/domain/instructor/page";

import { createBrowserRouter } from "react-router";

export const router = createBrowserRouter([
  // 루트 경로
  {
    path: "/",
    Component: StudentLayout,
    children: [
      {
        index: true,
        Component: S.HomePage,
      },
    ],
  },

  // 학생 페이지
  {
    path: "/student",
    Component: StudentLayout,
    children: [
      { path: "home", Component: S.HomePage },
      { path: "mypage", Component: S.MyPage },
      { path: "mypage/heart", Component: S.HeartPage },
      { path: "mypage/current-course", Component: S.CurrentCoursePage },
      { path: "course/search", Component: S.SearchResultPage },
    ],
  },

  // 강좌 상세 페이지
  {
    path: "/student/course/:courseId",
    Component: StickyLogoLayout,

    children: [
      {
        path: "dashboard",
        Component: CourseDashboardLayout,
        children: [
          {
            Component: S.CourseDetailDashboardPage,
          },
        ],
      },
      {
        Component: CourseDetailLayout,
        children: [
          {
            path: "curriculum",
            Component: () => (
              <div className="h-[100vh] bg-amber-200">커리큘럼</div>
            ),
          },
          {
            path: "reviews",
            Component: () => <div>리뷰</div>,
          },
          {
            path: "assignments",
            Component: () => <div>과제</div>,
          },
          {
            path: "questions",
            Component: () => <div>질문</div>,
          },
        ],
      },
    ],
  },

  // 강사 페이지
  {
    path: "/instructor",
    Component: InstructorLayout,
    children: [
      { path: "home", Component: I.HomePage },
      { path: "mypage", Component: I.MyPage },
      { path: "course/manage", Component: I.ManageCoursePage },
    ],
  },
  { path: "/instructor/course/create", Component: I.CreateCoursePage },

  // 강사 강좌 상세 페이지
  {
    path: "/instructor/course/:courseId",
    Component: StickyLogoLayout,
    children: [
      {
        path: "dashboard",
        Component: CourseDashboardLayout,
        children: [
          {
            index: true,
            Component: I.CourseDetailPage,
          },
          {
            path: "curriculum",
            Component: () => (
              <div className="h-[100vh] bg-amber-200">커리큘럼</div>
            ),
          },
          {
            path: "reviews",
            Component: () => <div>리뷰</div>,
          },
          {
            path: "assignments",
            Component: () => <div>과제</div>,
          },
          {
            path: "questions",
            Component: () => <div>질문</div>,
          },
        ],
      },
    ],
  },
]);
