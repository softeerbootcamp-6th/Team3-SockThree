import Logo from "@/assets/logo/logo.svg?react";
import Button from "@/shared/components/Button";

interface NavbarProps {
  myPageItem: React.ReactNode;
  children?: React.ReactNode;
}

const Navbar = ({ myPageItem, children }: NavbarProps) => {
  return (
    <aside className="sticky top-0 ml-[2.625rem] flex h-screen w-[6.8125rem] flex-col items-center justify-between bg-transparent pt-[4.5625rem] pb-[5.9431rem]">
      <div className="flex flex-col items-center justify-between gap-[2.25rem]">
        {/* 로고 */}
        <div className="flex flex-col items-center gap-4">
          <Logo />
        </div>

        {/* 메뉴 리스트 (중앙 정렬) */}
        <nav className="flex flex-col items-center">
          <ul>{children}</ul>
        </nav>
      </div>

      {/* 하단 마이페이지 및 로그아웃 */}
      <div className="flex flex-col items-center gap-2">
        {/* 마이페이지 아이템 */}
        <ul className="flex flex-col items-center">{myPageItem}</ul>

        {/* 로그아웃 버튼 */}
        <Button variant={"ghost"} size={"xs"}>
          로그아웃
        </Button>
      </div>
    </aside>
  );
};

export default Navbar;
