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

const userName = "홍길동";
const recommendLength = originalListItems.length;

const RecommendedCourseListView = () => {
  return (
    <div className="flex flex-col items-start gap-[1.1875rem]">
      <div className="flex flex-row items-end justify-end gap-[1.0625rem]">
        <p className="typo-title-0">
          {userName}님을 위한 <br /> 추천 강좌 리스트예요
        </p>
        <span className="typo-body-1 text-gray-500">
          추천 강좌 TOP{recommendLength}
        </span>
      </div>
      <div className={"relative h-[10.4375rem] w-[51.6875rem]"}>
        <RecommendedCourseListCarousel originalListItems={originalListItems} />
        <div className="pointer-events-none absolute inset-0 bg-gradient-to-b from-transparent to-bg" />
      </div>
    </div>
  );
};

export default RecommendedCourseListView;
