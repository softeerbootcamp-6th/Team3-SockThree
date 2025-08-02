// CardContainer.tsx
import { useState } from "react";
import HoverExpandCourseCard from "@/user/student/component/HoverExpandCourseCard";
import { fakerKO as faker } from "@faker-js/faker";

const createRandomCards = () => {
  return {
    id: faker.number.int({ min: 1, max: 100 }),
    title: faker.lorem.sentence(),
    description: faker.lorem.sentence(),
    tags: faker.helpers.arrayElements(["운동", "골프", "1개월"]),
    teacherId: faker.number.int({ min: 1, max: 10 }),
    teacherImg: faker.image.avatar(),
    nowStudents: faker.number.int({ min: 1, max: 15 }),
    maxStudents: faker.number.int({ min: 15, max: 30 }),
    courseImg: faker.image.urlPicsumPhotos({ width: 600, height: 400 }),
  };
};

const cards = faker.helpers.multiple(createRandomCards, {
  count: 7,
});

export default function HoverExpandCourseCardView() {
  const [activeId, setActiveId] = useState<number>(1);

  return (
    <div className="flex items-center gap-2 px-4">
      {cards.map((card) => (
        <div
          key={card.id}
          onMouseEnter={() => setActiveId(card.id)}
          className="transition-all duration-300"
        >
          <HoverExpandCourseCard cardData={card} isActive={card.id === activeId} />
        </div>
      ))}
    </div>
  );
}
