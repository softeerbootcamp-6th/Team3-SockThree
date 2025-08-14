import InstructorNavbar from "@/domain/instructor/component/InstructorNavbar";

import { Outlet } from "react-router";

const InstructorLayout = () => {
  return (
    <div className="flex h-screen flex-row gap-3">
      <InstructorNavbar />
      <Outlet />
    </div>
  );
};

export default InstructorLayout;
