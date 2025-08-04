import { useEffect, useState, useRef, useCallback } from "react";
import RecommendedCourseList from "../component/RecommendedCourseList";
import { fakerKO as faker } from "@faker-js/faker";

const createRandomLists = (index: number) => ({
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
const AUTO_INTERVAL = 3000; // 3초

const RecommendedCourseListView = () => {
  const [index, setIndex] = useState(0);
  const [transition, setTransition] = useState(true);
  const intervalRef = useRef<NodeJS.Timeout | null>(null);

  const createCarouselList = ({
    listPerPage,
    originalLists,
  }: {
    listPerPage: number;
    originalLists: listData[];
  }): listData[] => {
    const repeatedList = Array.from(
      { length: listPerPage },
      () => originalLists
    ).flat();

    // 첫 번째 리스트로 자연스럽게 넘어가기 위해 요소 추가
    const overflowItems = originalLists.slice(0, listPerPage);

    return [...repeatedList, ...overflowItems];
  };

  const carouselList = createCarouselList({
    listPerPage: COURSES_PER_PAGE,
    originalLists: originalLists,
  });

  const startAutoSlide = useCallback(() => {
    if (intervalRef.current) return;
    intervalRef.current = setInterval(() => {
      setIndex((prev) => {
        if (prev === originalLists.length - 1) {
          setIndex(originalLists.length);

          // 마지막 transition이 끝난 후에 transition을 제거하고 첫 번째로 점프
          setTimeout(() => {
            setTransition(false);
            setIndex(0);
          }, 700);
        } else {
          setTransition(true);
        }
        return prev + 1;
      });
    }, AUTO_INTERVAL);
  }, []);

  const stopAutoSlide = useCallback(() => {
    if (intervalRef.current) {
      clearInterval(intervalRef.current);
      intervalRef.current = null;
    }
  }, []);

  useEffect(() => {
    startAutoSlide();
    return () => stopAutoSlide();
  }, [startAutoSlide, stopAutoSlide]);

  const offsetY = index * ITEM_HEIGHT * COURSES_PER_PAGE;

  return (
    <div className="h-[10.4375rem] w-full overflow-hidden">
      <ul
        onMouseEnter={stopAutoSlide}
        onMouseLeave={startAutoSlide}
        className={`transition-transform ${
          transition ? "duration-700 ease-in-out" : "transition-none"
        }`}
        style={{
          transform: `translateY(-${offsetY}px)`,
        }}
      >
        {carouselList.map((course, i) => (
          <RecommendedCourseList
            key={`${course.teacherName}-${i}`}
            listData={course}
          />
        ))}
      </ul>
    </div>
  );
};

export default RecommendedCourseListView;
