import Navbar from "@/shared/components/Navbar/Navbar";
import { Outlet } from "react-router";

import HomeIcon from "@/assets/icons/default/home.svg?react";
import SearchIcon from "@/assets/icons/default/search.svg?react";
import CourseIcon from "@/assets/icons/default/course.svg?react";

import { useState } from "react";
import NavbarItem from "@/shared/components/Navbar/NavbarItem";

import { useLocation } from "react-router";
import { fakerKO as faker } from "@faker-js/faker";

const StudentLayout = () => {
  const navbarMenus = [
    { icon: HomeIcon, label: "홈", routePath: "/student/home" },
    {
      icon: SearchIcon,
      label: "강의 검색",
      routePath: "/student/course-search",
    },
    { icon: CourseIcon, label: "???", routePath: "/student/course" },
  ];

  const location = useLocation();
  const [selectedPath, setSelectedPath] = useState(location.pathname);

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
      <Navbar myPageItem={myPageItem}>{navbarItems}</Navbar>
      <Outlet />
    </div>
  );
};

export default StudentLayout;
