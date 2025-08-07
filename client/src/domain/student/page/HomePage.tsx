import HoverExpandCourseCardView from "../view/HoverExpandCourseCardView";
import RecommendedCourseListView from "../view/RecommendedCourseListView";

const HomePage = () => {
  return (
    <div className="w-full pt-[4rem] pl-[2.8125rem]">
      <div className="flex w-full items-center justify-between">
        <div>
          <div>
            <span className="typo-title-0">마감까지</span>
          </div>
        </div>
      </div>
      <HoverExpandCourseCardView />
      <RecommendedCourseListView />
    </div>
  );
};

export default HomePage;
