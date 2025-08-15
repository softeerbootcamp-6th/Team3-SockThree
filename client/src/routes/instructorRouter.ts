import { createBrowserRouter } from "react-router";
import InstructorLayout from "@/shared/layout/InstructorLayout";
import * as I from "@/domain/instructor/page";
import StickyLogoLayout from "@/shared/layout/StickyLogoLayout";

export const createInstructorRouter = () =>
  createBrowserRouter([
    {
      path: "/",
      Component: InstructorLayout,
      children: [
        { index: true, Component: I.HomePage },
        { path: "home", Component: I.HomePage },
        { path: "my-page", Component: I.MyPage },
        { path: "course/manage", Component: I.ManageCoursePage },
        { path: "course/create", Component: I.CreateCoursePage },
      ],
    },
    {
      path: "/course/:courseId/*",
      Component: StickyLogoLayout,
      children: [{ path: "*", Component: I.CourseDetailPage }],
    },
  ]);
