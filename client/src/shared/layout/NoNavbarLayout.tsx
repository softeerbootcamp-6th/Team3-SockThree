import { Outlet } from "react-router";
import Logo from "@/assets/logo/logo.svg?react";

const NoNavbarLayout = () => {
  return (
    <div className="relative flex h-screen w-full flex-col">
      <Logo className="absolute top-[4.5625rem] left-[2.625rem] h-[3rem] w-[6.8125rem]" />
      <Outlet />
    </div>
  );
};

export default NoNavbarLayout;
