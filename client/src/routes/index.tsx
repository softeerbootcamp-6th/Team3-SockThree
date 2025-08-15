import { createStudentRouter } from "@/routes/studentRouter";
import { createInstructorRouter } from "@/routes/instructorRouter";

const getUserType = () => {
  const port = window.location.port;
  switch (port) {
    case "5173":
      return "student";
    case "5174":
      return "instructor";
    default:
      return "student";
  }
};

export const router =
  getUserType() === "instructor"
    ? createInstructorRouter()
    : createStudentRouter();
