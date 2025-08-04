import React from "react";
import Logo from "@/assets/logo/logo.svg?react";
import Button from "@/shared/components/Button";

interface SidebarProps {
  userImg?: string;
  children: React.ReactNode;
}

const Sidebar = ({ userImg, children }: SidebarProps) => {
  return (
    <aside className="ml-[2.625rem] flex h-screen w-[6.8125rem] flex-col items-center justify-between bg-transparent pt-[4.5625rem] pb-[5.9431rem]">
      {/* 로고 */}
      <div className="flex flex-col items-center justify-between gap-[5.38rem]">
        <div className="flex flex-col items-center gap-4">
          <Logo />
        </div>

        {/* 메뉴 리스트 (중앙 정렬) */}
        <nav className="flex flex-col items-center">
          <ul>
            {React.Children.map(children, (child, index) => (
              <li className="h-[7.75rem] w-[5.9375rem]" key={index}>
                {child}
              </li>
            ))}
          </ul>
        </nav>
      </div>

      {/* 하단 마이페이지 및 로그아웃 */}
      <div className="flex flex-col items-center gap-2">
        <button className="flex h-[5.1875rem] w-[4.8125rem] flex-col items-center justify-between">
          {/* 사용자 이미지 */}
          <img
            src={userImg}
            alt="Profile"
            className="h-[2.75rem] w-[2.75rem] rounded-full object-cover"
          />
          <span className="typo-label-2 text-gray-500">마이페이지</span>
        </button>
        {/* 로그아웃 버튼 */}
        <Button variant={"ghost"} size={"xs"}>
          로그아웃
        </Button>
      </div>
    </aside>
  );
};

export default Sidebar;
