import HomeIcon from "@/assets/icons/default/home.svg?react";
import SearchIcon from "@/assets/icons/default/search.svg?react";

import NavbarItem from "@/domain/student/component/navbar/NavbarItem";
import Navbar from "@/domain/student/component/navbar/Navbar";

import { useState } from "react";
import { useLocation } from "react-router";
import { fakerKO as faker } from "@faker-js/faker";

const StudentNavbar = () => {
  const navbarMenus = [
    { icon: HomeIcon, label: "홈", routePath: "/student/home" },
    {
      icon: SearchIcon,
      label: "강의 검색",
      routePath: "/student/course/search",
    },
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
  return <Navbar myPageItem={myPageItem}>{navbarItems}</Navbar>;
};

export default StudentNavbar;
