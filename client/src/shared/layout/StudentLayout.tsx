import StudentNavbar from "@/domain/student/component/StudentNavbar";

import { Outlet } from "react-router";

const StudentLayout = () => {
  return (
    <div className="flex-start flex h-screen flex-row gap-[3rem]">
      <StudentNavbar />
      <Outlet />
    </div>
  );
};

export default StudentLayout;
