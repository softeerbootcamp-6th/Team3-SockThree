import Navbar from "@/shared/components/Navbar/Navbar";
import { Outlet } from "react-router";

import HomeIcon from "@/assets/icons/default/home.svg?react";
import SearchIcon from "@/assets/icons/default/search.svg?react";

import { useState } from "react";
import NavbarItem from "@/shared/components/Navbar/NavbarItem";

const StudentLayout = () => {
  return (
    <div>
      {renderStudentNavbar()}
      <Outlet />
    </div>
  );
};

const renderStudentNavbar = () => {
  const navbarMenus = [
    { icon: HomeIcon, label: "홈", routePath: "/student/home" },
    {
      icon: SearchIcon,
      label: "강의 검색",
      routePath: "/student/course-search",
    },
  ];
  const [selectedPath, setSelectedPath] = useState("home");

  const handleItemClick = (path: string) => {
    setSelectedPath(path);
  };

  const navbarItems = navbarMenus.map((menu) => (
    <NavbarItem
      key={menu.routePath}
      icon={menu.icon}
      label={menu.label}
      routePath={menu.routePath}
      isSelected={selectedPath === menu.routePath}
      onClick={() => handleItemClick(menu.routePath)}
    />
  ));

  return <Navbar>{navbarItems}</Navbar>;
};

export default StudentLayout;
