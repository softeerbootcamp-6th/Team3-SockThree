import HoverExpandCourseCardView from "../view/HoverExpandCourseCardView";
import RecommendedCourseListView from "../view/RecommendedCourseListView";

import CheckIcon from "@/assets/icons/default/check.svg?react";

const StudentHomePage = () => {
  return (
    <div className="w-full pt-[4rem] pl-[2.8125rem]">
      <div className="flex w-full items-center justify-between">
        <div>
          <div className="flex items-center gap-[1.1875rem]">
            <span className="typo-title-0">마감까지</span>
            <CheckIcon className="-z-10 h-[4.0625rem]" />
          </div>
          <span className="typo-title-0">얼마 남지 않았어요!</span>
        </div>
        <div className="flex items-center gap-[1.1875rem]">
          <span className="typo-label-0">오늘의 추천 강의</span>
        </div>
      </div>
      <HoverExpandCourseCardView />
      <RecommendedCourseListView />
    </div>
  );
};

export default StudentHomePage;
