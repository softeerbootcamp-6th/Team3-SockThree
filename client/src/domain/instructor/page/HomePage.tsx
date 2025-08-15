import CourseCardContainer from "@/domain/instructor/component/course/CourseCardContainer";
import Button from "@/shared/components/Button";
import CourseTab from "@/shared/components/course/CourseTab";
import { fakerKO as faker } from "@faker-js/faker";
import { useNavigate } from "react-router";

const HomePage = () => {
  const navigate = useNavigate();
  const instructorName = "하하하";
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
      rating: faker.number.float({ min: 1, max: 5 }),
      reviews: faker.number.int({ min: 0, max: 500 }),
      price: faker.number.int({ min: 100000, max: 5000000 }),
      isHeartButton: true,
    };
  };

  const currentCourse = faker.helpers.multiple(createCourseData, {
    count: 30,
  });

  const tabItems = [
    {
      label: "진행 중인 강좌",
      content: <CourseCardContainer cardItems={currentCourse} />,
    },
    { label: "종료된 강좌", content: <CourseCardContainer cardItems={[]} /> },
  ];

  const handleNewCourseClick = () => {
    navigate("/instructor/course/create");
  };

  return (
    <div className="flex w-full flex-col gap-[1.25rem] pt-[4.9375rem] pr-[3.9375rem]">
      <div className="flex w-full items-center justify-between">
        <h1 className="typo-title-0">{instructorName} 강사님, 안녕하세요</h1>
      </div>
      <div className="relative">
        <div className="absolute top-0 right-20 z-300">
          <Button variant="outline" onClick={handleNewCourseClick}>
            신규 강좌 등록하기
          </Button>
        </div>
        <CourseTab tabItems={tabItems} />
      </div>
    </div>
  );
};

export default HomePage;
