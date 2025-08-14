import StudentNavbar from "@/domain/student/component/StudentNavbar";

import { Outlet } from "react-router";

const StudentLayout = () => {
  return (
    <div className="flex h-screen flex-row justify-between">
      <StudentNavbar />
      <Outlet />
    </div>
  );
};

export default StudentLayout;
