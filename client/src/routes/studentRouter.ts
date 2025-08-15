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
        { path: "my-page", Component: S.MyPage },
        { path: "my-page/heart", Component: S.HeartPage },
        { path: "my-page/my-course", Component: S.MyCoursePage },
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
