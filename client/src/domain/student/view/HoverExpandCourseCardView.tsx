// CardContainer.tsx
import { useState } from "react";
import HoverExpandCourseCard from "@/domain/student/component/HoverExpandCourseCard";
import { fakerKO as faker } from "@faker-js/faker";

const createRandomCards = () => {
  return {
    id: faker.number.int({ min: 1, max: 100 }),
    title: faker.lorem.words({ min: 1, max: 2 }),
    description: faker.lorem.sentence({ min: 3, max: 5 }),
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
  const [activeId, setActiveId] = useState<number>(expandedCardIndex);

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
