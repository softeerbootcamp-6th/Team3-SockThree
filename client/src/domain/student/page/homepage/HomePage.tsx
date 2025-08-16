import ExpandSearchBar from "@/domain/student/component/homepage/ExpandSearchBar";
import HoverExpandCourseCardView from "@/domain/student/view/HoverExpandCourseCardView";
import RecommendedCourseListView from "@/domain/student/view/RecommendedCourseListView";

import RotatedCheckIcon from "@/assets/icons/default/rotated-check.svg?react";
import Button from "@/shared/components/Button";

const HomePage = () => {
  return (
    <div className="flex h-full w-full flex-col overflow-y-auto px-[3.5625rem] pt-[4rem] pb-[3.75rem]">
      <div className="flex w-full justify-between pb-[2.6875rem]">
        <div>
          <div className="flex items-center gap-[1.1875rem]">
            <span className="typo-title-0 whitespace-nowrap">마감까지</span>
            <RotatedCheckIcon className="h-[4.0625rem]" />
          </div>
          <span className="typo-title-0 whitespace-nowrap">
            얼마 남지 않았어요!
          </span>
        </div>
        <ExpandSearchBar />
      </div>
      <HoverExpandCourseCardView />
      <hr className="my-[3.375rem] h-[.0625rem] w-full text-gray-500" />
      <div className="flex h-[21.5rem] w-full flex-row items-end justify-between">
        <RecommendedCourseListView />
        <Button variant="outline" textSize="title2">
          추천 강좌 둘러보기
        </Button>
      </div>
    </div>
  );
};

export default HomePage;
