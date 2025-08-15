import { useState } from "react";

interface TabItem {
  label: string;
  content: React.ReactNode;
}
interface CourseTabProps {
  tabItems?: TabItem[];
}

const CourseTab = ({
  tabItems = [
    {
      label: "진행 중인 강좌",
      content: <div>진행 중인 강좌 콘텐츠</div>,
    },
    { label: "종료된 강좌", content: <div>종료된 강좌 콘텐츠</div> },
  ],
}: CourseTabProps) => {
  const [activeTab, setActiveTab] = useState(0);

  const handleTabClick = (index: number) => {
    setActiveTab(index);
  };

  return (
    <div className="flex w-full flex-col">
      {/* 탭 바 영역 */}
      <nav className="sticky top-0 z-100 w-full bg-bg py-[1rem]">
        <div
          role="tablist"
          className="typo-body-1 flex h-[2.8125rem] items-end gap-8"
        >
          {tabItems.map(({ label }, index) => (
            <button
              className={`flex w-[16rem] cursor-pointer justify-center border-b-3 pb-[.4375rem] hover:text-black focus:outline-none ${activeTab === index ? "border-main-400 text-black" : "border-transparent text-gray-600"}`}
              key={index}
              onClick={() => handleTabClick(index)}
            >
              <span className="pb-[0.3rem]">{label}</span>
            </button>
          ))}
        </div>
      </nav>
      {/* 콘텐츠 영역 */}
      <div>{tabItems[activeTab]?.content}</div>
    </div>
  );
};

export default CourseTab;
