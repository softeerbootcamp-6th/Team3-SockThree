import ExpandSearchBar from "@/domain/student/component/ExpandSearchBar";
import HoverExpandCourseCardView from "../view/HoverExpandCourseCardView";
import RecommendedCourseListView from "../view/RecommendedCourseListView";

import CheckIcon from "@/assets/icons/default/check.svg?react";
import Button from "@/shared/components/Button";

const StudentHomePage = () => {
  return (
    <div className="h-[100%] w-[100%] pt-[4rem] pr-[3.5625rem] pb-[3.75rem] pl-[2.8125rem]">
      <div className="flex w-full items-center justify-between pb-[2.6875rem]">
        <div>
          <div className="flex items-center gap-[1.1875rem]">
            <span className="typo-title-0 whitespace-nowrap">마감까지</span>
            <CheckIcon className="h-[4.0625rem]" />
          </div>
          <span className="typo-title-0 whitespace-nowrap">
            얼마 남지 않았어요!
          </span>
        </div>
        <div className="flex flex-col items-center gap-[1.1875rem]">
          <ExpandSearchBar />
        </div>
      </div>
      <HoverExpandCourseCardView />
      <div className="mt-[3.8125rem] mb-[2.8125rem] flex h-[.0625rem] w-[91.6875rem] bg-gray-500" />
      <div className="flex h-[21.5rem] w-[92.1875rem] flex-row items-end justify-between">
        <RecommendedCourseListView />
        <Button variant={"outline"} textSize={"title2"}>
          추천 강좌 둘러보기
        </Button>
      </div>
    </div>
  );
};

export default StudentHomePage;
