import RecommendedCourseList from "../component/RecommendedCourseList";
import { useCarousel } from "../hook/useCarousel";
import { fakerKO as faker } from "@faker-js/faker";

const createRandomLists = (index: number) => ({
  courseId: faker.number.int({ min: 1, max: 100 }),
  teacherImg: faker.image.avatar(),
  teacherName: (index + 1).toString(),
  courseTitle: faker.lorem.words({ min: 1, max: 2 }),
  courseType: faker.helpers.arrayElement(["운동", "골프", "1개월"]),
  courseDays: faker.helpers.arrayElements(["월", "화", "수", "목", "금"], {
    min: 1,
    max: 3,
  }),
  nowStudents: faker.number.int({ min: 1, max: 15 }),
  maxStudents: faker.number.int({ min: 15, max: 30 }),
});

const originalLists = Array.from({ length: 10 }, (_, i) =>
  createRandomLists(i)
);

interface listData {
  courseId: number;
  teacherImg: string;
  teacherName: string;
  courseTitle: string;
  courseType: string;
  courseDays: string[];
  nowStudents: number;
  maxStudents: number;
}

const COURSES_PER_PAGE = 3;
const ITEM_HEIGHT = 56; // px
const AUTO_INTERVAL = 3000;
const TRANSITION_DURATION = 700;

const RecommendedCourseListView = () => {
  const {
    carouselList,
    offsetY,
    isTransition,
    handleMouseEnter,
    handleMouseLeave,
  } = useCarousel<listData>({
    originalList: originalLists,
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
        {carouselList.map((course) => (
          <RecommendedCourseList key={course.courseId} listData={course} />
        ))}
      </ul>
    </div>
  );
};

export default RecommendedCourseListView;
