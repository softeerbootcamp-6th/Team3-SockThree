import { Outlet } from "react-router";
import Logo from "@/assets/logo/logo.svg?react";

const LogoHeaderLayout = () => {
  return (
    <div className="relative flex h-screen w-full flex-col">
      <header className="sticky top-0 left-[2.625rem] z-50 flex h-fit bg-bg py-[1rem] pl-[1rem]">
        <Logo className="h-[3rem] w-[6.8125rem]" />
      </header>
      <main className="mx-auto flex max-w-[1720px] min-w-[1280px] flex-col items-center justify-center">
        <Outlet />
      </main>
    </div>
  );
};

export default LogoHeaderLayout;
