import Chips from "@/shared/components/Chips";
import PersonIcon from "@/assets/icons/default/person.svg?react";

type HoverExpandCourseCardProps = {
  cardData: {
    id: number;
    title: string;
    description?: string;
    tags?: string[];
    teacherName?: string;
    teacherImg?: string;
    nowStudents?: number;
    maxStudents?: number;
    courseImg?: string;
  };
  isActive: boolean;
};

type CardData = HoverExpandCourseCardProps["cardData"];

export default function HoverExpandCourseCard({
  cardData,
  isActive,
}: HoverExpandCourseCardProps) {
  return (
    <div
      className={`h-[21.3125rem] transition-all duration-300 ${isActive ? `w-[54.375rem] rounded-[1.875rem] px-[1.4375rem] py-[1.5rem]` : "w-[5rem] rounded-[3.125rem] opacity-60 brightness-50"} cursor-pointer bg-cover bg-center`}
      style={{
        backgroundImage: `url(${cardData.courseImg})`,
      }}
    >
      {!isActive && (
        <div className="flex h-full flex-col justify-end p-4 text-white"></div>
      )}
      {isActive && (
        <div className="flex h-full flex-col justify-between text-white">
          {cardTopContainer(cardData)}
          {cardBottomContainer(cardData)}
        </div>
      )}
    </div>
  );
}

const cardTopContainer = (cardData: CardData) => {
  return (
    <div className="flex w-[824px] justify-between">
      <div className="flex items-center gap-0.5">
        <img
          src={cardData.teacherImg}
          alt="Teacher"
          className="h-[1.8125rem] w-[1.8125rem] rounded-full"
        />
        <span className="typo-body-6">{cardData.teacherName}</span>
      </div>
      <div className="flex items-center gap-[1.06rem]">
        {cardData.tags?.map((tag, index) => (
          <Chips key={index} type="field" title={tag} interactive={false} />
        ))}
      </div>
    </div>
  );
};

const cardBottomContainer = (cardData: CardData) => {
  return (
    <div className="flex w-full flex-row justify-between gap-2">
      <div className="flex h-[4.8125rem] w-[13.75rem] flex-col">
        <p className="typo-body-6 h-[1.3125rem] truncate">
          {cardData.description}
        </p>
        <p className="typo-title-1 h-[3rem] truncate">{cardData.title}</p>
      </div>
      <div className="W-[32.0625rem] flex flex-col">
        <div className="flex flex-row gap-[var(--spacing-1)]">
          <PersonIcon className="h-[1.5625rem] w-[1.625rem]" />
          <span className="text-sm">{`${cardData.nowStudents} / ${cardData.maxStudents}`}</span>
        </div>
      </div>
    </div>
  );
};
