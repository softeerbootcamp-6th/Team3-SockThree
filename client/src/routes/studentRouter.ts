import { createBrowserRouter } from "react-router";
import StudentLayout from "@/shared/layout/StudentLayout";
import LogoHeaderLayout from "@/shared/layout/LogoHeaderLayout";

import * as S from "@/domain/student/page";

export const createStudentRouter = () =>
  createBrowserRouter([
    {
      path: "/",
      Component: StudentLayout,
      children: [
        { index: true, Component: S.HomePage },
        { path: "home", Component: S.HomePage },
        { path: "mypage", Component: S.MyPage },
        { path: "mypage/heart", Component: S.HeartPage },
        { path: "mypage/current-course", Component: S.CurrentCoursePage },
        { path: "mypage/end-course", Component: S.EndCoursePage },
        { path: "course/search", Component: S.SearchResultPage },
      ],
    },
    {
      path: "/course/:courseId/*",
      Component: LogoHeaderLayout,
      children: [
        {
          path: "*",
          Component: S.CourseDetailPage,
        },
      ],
    },
  ]);
