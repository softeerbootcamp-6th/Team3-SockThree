import Navbar from "@/shared/components/Navbar/Navbar.tsx";
import NavbarItem from "@/shared/components/Navbar/NavbarItem.tsx";

import { useState } from "react";
import { Outlet, useLocation } from "react-router";

import { fakerKO as faker } from "@faker-js/faker";

const InstructorLayout = () => {
  const location = useLocation();
  const [selectedPath, setSelectedPath] = useState(location.pathname);

  const handleItemClick = (path: string) => {
    setSelectedPath(path);
  };

  const myPageItem = (
    <NavbarItem
      imgUrl={faker.image.avatar()}
      label="마이페이지"
      routePath="/student/mypage"
      isSelected={selectedPath === "/student/mypage"}
      onClick={() => handleItemClick("/student/mypage")}
    />
  );

  return (
    <div className="flex h-screen flex-row justify-between">
      <Navbar myPageItem={myPageItem} />
      <Outlet />
    </div>
  );
};

export default InstructorLayout;
