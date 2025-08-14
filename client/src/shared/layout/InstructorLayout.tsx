import InstructorNavbar from "@/domain/instructor/component/InstructorNavbar";

import { Outlet } from "react-router";

const InstructorLayout = () => {
  return (
    <div className="flex h-screen flex-row justify-between">
      <InstructorNavbar />
      <Outlet />
    </div>
  );
};

export default InstructorLayout;
