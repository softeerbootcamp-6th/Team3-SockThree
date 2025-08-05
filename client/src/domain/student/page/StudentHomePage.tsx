import HoverExpandCourseCardView from "../view/HoverExpandCourseCardView";
import RecommendedCourseListView from "../view/RecommendedCourseListView";

const StudentHomePage = () => {
  return (
    <div className="flex w-full max-w-4xl flex-col items-center justify-center space-y-6 p-6">
      <HoverExpandCourseCardView />
      <RecommendedCourseListView />
    </div>
  );
};

export default StudentHomePage;
