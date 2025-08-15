import { createBrowserRouter } from "react-router";

/* Domain */
import * as S from "@/domain/student/page/index";
import * as I from "@/domain/instructor/page/index";

/* Layout */
import RootLayout, { rootLoader } from "@/routes/RootLayout";
import StudentLayout from "@/shared/layout/StudentLayout";
import InstructorLayout from "@/shared/layout/InstructorLayout";
import LogoHeaderLayout from "@/shared/layout/LogoHeaderLayout";

/* Error Pages */
import Error404 from "@/shared/page/error/Error404";

/* Login Page */
import LoginPage from "@/shared/page/login/LoginPage";

/* 권한에 따른 라우팅 */
import { RouteByRole } from "@/routes/RouteByRole";

export const router = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />, // @TODO: 로그인 페이지 컴포넌트
  },
  {
    path: "/",
    element: <RootLayout />,
    loader: rootLoader,
    children: [
      {
        path: "/",
        element: (
          <RouteByRole
            components={{
              student: StudentLayout,
              instructor: InstructorLayout,
            }}
          />
        ),
        children: [
          {
            index: true,
            path: "/",
            element: (
              <RouteByRole
                components={{ student: S.HomePage, instructor: I.HomePage }}
              />
            ),
          },
          {
            path: "/home",
            element: (
              <RouteByRole
                components={{ student: S.HomePage, instructor: I.HomePage }}
              />
            ),
          },
          {
            path: "mypage",
            element: (
              <RouteByRole
                components={{ student: S.MyPage, instructor: I.MyPage }}
              />
            ),
          },
          {
            path: "mypage/heart", // required 학생
            element: <RouteByRole components={{ student: S.HeartPage }} />,
          },
          {
            path: "mypage/current", // required 학생
            element: (
              <RouteByRole components={{ student: S.CurrentCoursePage }} />
            ),
          },
          {
            path: "mypage/end", // required 학생
            element: <RouteByRole components={{ student: S.EndCoursePage }} />,
          },
          {
            path: "course/search", // required 학생
            element: (
              <RouteByRole components={{ student: S.SearchResultPage }} />
            ),
          },
          {
            path: "course/manage", // required 강사
            element: (
              <RouteByRole components={{ instructor: I.ManageCoursePage }} />
            ),
          },
        ],
      },
      {
        path: "/course/:courseId/*",
        element: <LogoHeaderLayout />,
        children: [
          {
            path: "*",
            element: (
              <RouteByRole
                components={{
                  student: S.CourseDetailPage,
                  instructor: I.CourseDetailPage,
                }}
              />
            ),
          },
        ],
      },
      {
        path: "/course/create",
        element: (
          <RouteByRole components={{ instructor: I.CreateCoursePage }} />
        ),
      },
      {
        path: "*", // 모든 잘못된 경로
        element: <Error404 />,
      },
    ],
  },
]);
