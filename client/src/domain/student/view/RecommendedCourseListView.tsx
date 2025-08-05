import RecommendedCourseListCarousel from "@/domain/student/component/RecommendedCourseListCarousel";
import { fakerKO as faker } from "@faker-js/faker";

const createRandomLists = () => ({
  courseId: faker.number.int({ min: 1, max: 100 }),
  teacherImg: faker.image.avatar(),
  teacherName: faker.helpers.arrayElement([
    "최지애",
    "정연주",
    "이정빈",
    "한경준",
  ]),
  courseTitle: faker.lorem.words({ min: 2, max: 3 }),
  courseType: faker.helpers.arrayElement(["운동", "골프"]),
  courseDays: faker.helpers.arrayElements(["월", "화", "수", "목", "금"], {
    min: 1,
    max: 3,
  }),
  nowStudents: faker.number.int({ min: 1, max: 15 }),
  maxStudents: faker.number.int({ min: 15, max: 30 }),
  courseStatus: faker.helpers.arrayElement(["진행중", "마감"]), // 확인: status 삭제될 수도 있음
});

const originalListItems = Array.from({ length: 10 }, () => createRandomLists());

const RecommendedCourseListView = () => {
  return (
    <RecommendedCourseListCarousel originalListItems={originalListItems} />
  );
};

export default RecommendedCourseListView;
