// CardContainer.tsx
import { useState } from "react";
import HoverExpandCourseCard from "@/domain/student/component/HoverExpandCourseCard";
import { fakerKO as faker } from "@faker-js/faker";

const createRandomCards = () => {
  return {
    id: faker.number.int({ min: 1, max: 100 }),
    title: faker.helpers.arrayElement([
      "운동으로 스트레스 해소하기",
      "골프 배우기",
      "1개월 동안의 요가 클래스",
    ]),
    description: faker.helpers.arrayElement([
      "이 코스는 운동을 통해 스트레스를 해소하는 방법을 배웁니다.",
      "골프의 기본기를 배우고, 필드에서의 경험을 쌓습니다.",
      "1개월 동안 요가를 통해 몸과 마음의 균형을 찾습니다.",
    ]),
    tags: faker.helpers.arrayElements(["운동", "골프", "1개월"]),
    teacherName: faker.person.firstName(),
    teacherImg: faker.image.avatar(),
    nowStudents: faker.number.int({ min: 1, max: 15 }),
    maxStudents: faker.number.int({ min: 15, max: 30 }),
    courseImg: faker.image.urlPicsumPhotos({
      width: 600,
      height: 400,
      grayscale: false,
      blur: 0,
    }),
  };
};

const cards = faker.helpers.multiple(createRandomCards, {
  count: 7,
});

const HoverExpandCourseCardView = () => {
  const expandedCardIndex = 2;
  const [activeId, setActiveId] = useState(expandedCardIndex);

  return (
    <div className="flex h-[21.3125rem] w-[92.1875rem] flex-row items-center gap-2 px-4">
      {cards.map((card, index) => (
        <div key={index} onMouseEnter={() => setActiveId(index)}>
          <HoverExpandCourseCard
            cardData={card}
            isActive={index === activeId}
          />
        </div>
      ))}
    </div>
  );
};

export default HoverExpandCourseCardView;
