import NavbarItem from "@/shared/components/Navbar/NavbarItem";
import Navbar from "@/shared/components/Navbar/Navbar";

import { useLocation } from "react-router";
import { useState } from "react";
import { fakerKO as faker } from "@faker-js/faker";

const NavbarContainer = () => {
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
  return <Navbar myPageItem={myPageItem} />;
};

export default NavbarContainer;
