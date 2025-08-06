import Navbar from "@/shared/components/Navbar/Navbar";
import { Outlet } from "react-router";

const InstructorLayout = () => {
  return (
    <div>
      <Navbar userImg="" />
      <Outlet />
    </div>
  );
};

export default InstructorLayout;
