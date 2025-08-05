import RecommendedCourseListItem from "@/domain/student/component/RecommendedCourseListItem";
import { useCarousel } from "@/domain/student/hook/useCarousel";

interface listItemData {
  courseId: number;
  teacherImg: string;
  teacherName: string;
  courseTitle: string;
  courseType: string;
  courseDays: string[];
  nowStudents: number;
  maxStudents: number;
  courseStatus?: string; // 확인: status 삭제될 수도 있음
}

const COURSES_PER_PAGE = 3;
const ITEM_HEIGHT = 56; // px
const AUTO_INTERVAL = 3000;
const TRANSITION_DURATION = 700;

interface RecommendListCarouselProps {
  originalListItems: listItemData[];
}

const RecommendedCourseListCarousel = ({
  originalListItems,
}: RecommendListCarouselProps) => {
  const {
    carouselListItems,
    offsetY,
    isTransition,
    handleMouseEnter,
    handleMouseLeave,
  } = useCarousel({
    originalListItems: originalListItems,
    itemHeight: ITEM_HEIGHT,
    itemsPerPage: COURSES_PER_PAGE,
    autoInterval: AUTO_INTERVAL,
    transitionDuration: TRANSITION_DURATION,
  });

  const transitionMap = {
    700: "duration-700 ease-in-out",
  };

  const transitionClass = isTransition
    ? transitionMap[TRANSITION_DURATION]
    : `transition-none`;

  return (
    <div className="h-[10.4375rem] w-full overflow-hidden">
      <ul
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
        className={`transition-transform ${transitionClass}`}
        style={{
          transform: `translateY(-${offsetY}px)`,
        }}
      >
        {carouselListItems.map((course: listItemData, index) => (
          <RecommendedCourseListItem
            key={`${course.courseId}-${index}`}
            listItemData={course}
          />
        ))}
      </ul>
    </div>
  );
};

export default RecommendedCourseListCarousel;
