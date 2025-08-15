import CourseProgressCard from "@/shared/components/course/CourseProgressCard";
import InfoChip from "@/shared/components/InfoChip";
import { fakerKO as faker } from "@faker-js/faker";

import { useNavigate } from "react-router";

const EndCoursePage = () => {
  const navigate = useNavigate();
  const studentName = "홍길동";
  const currentCourseLength: number = 30;

  const infoChipText =
    currentCourseLength === 0
      ? `${studentName}님은 아직 수강 완료한 강의가 없어요`
      : `${studentName}님은 ${currentCourseLength}개의 강좌를 수강하셨네요!`;

  const createCourseData = () => {
    return {
      courseId: faker.number.int({ min: 1, max: 100 }),
      imageUrl:
        "https://cdn.pixabay.com/photo/2025/04/25/12/13/nature-9558835_1280.jpg",
      teacherName: faker.person.fullName(),
      title: faker.helpers.arrayElement([
        "프론트엔드 개발자 양성 과정",
        "백엔드 개발자 양성 과정",
        "데이터베이스 관리 과정",
        "웹 디자인 기초 과정",
        "모바일 앱 개발 과정",
      ]),
      days: faker.helpers.arrayElements(["월", "화", "수", "목", "금"]),
      startTime: faker.helpers.arrayElement([
        "09:00",
        "10:00",
        "11:00",
        "13:00",
        "14:00",
      ]),
      endTime: faker.helpers.arrayElement([
        "11:00",
        "12:00",
        "13:00",
        "15:00",
        "16:00",
      ]),
      currentVideo: faker.number.int({ min: 1, max: 10 }),
      maxVideo: faker.number.int({ min: 10, max: 20 }),
    };
  };

  const course = faker.helpers.multiple(createCourseData, {
    count: currentCourseLength,
  });
  const handleCardClick = (id: number) => {
    navigate(`/student/course/${id}/dashboard`);
  };

  return (
    <div className="flex h-full w-full flex-col items-start gap-[1.25rem] overflow-auto pr-[3.0625rem] pb-[4.625rem] pl-[2.8125rem]">
      <div className="sticky top-0 z-30 flex w-full flex-col items-start gap-[1.25rem] bg-gradient-to-b from-bg from-85% to-transparent to-100% pt-[4.625rem] pb-[1.875rem]">
        <div className="flex w-full flex-row items-center gap-[2.25rem]">
          <span className="typo-title-0">수강 완료 강좌</span>
          <InfoChip text={infoChipText} />
        </div>
        <span className="typo-title-6 text-gray-600">
          총 {currentCourseLength}건
        </span>
      </div>
      <div className="grid grid-cols-3 gap-[2.125rem]">
        {course.map((course) => (
          <CourseProgressCard
            key={course.courseId}
            imageUrl={course.imageUrl}
            teacherName={course.teacherName}
            title={course.title}
            days={course.days}
            startTime={course.startTime}
            endTime={course.endTime}
            currentVideo={course.currentVideo}
            maxVideo={course.maxVideo}
            onClick={() => handleCardClick(course.courseId)}
          />
        ))}
      </div>
    </div>
  );
};

export default EndCoursePage;
