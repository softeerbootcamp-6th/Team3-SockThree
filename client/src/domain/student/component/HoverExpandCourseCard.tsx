import Chips from "@/shared/components/Chips";
import PersonIcon from "@/assets/icons/default/person.svg?react";
import ProgressBar from "@/shared/components/ProgressBar";
import RoundTooltip from "@/shared/components/RoundTooltip";

type HoverExpandCourseCardProps = {
  cardData: {
    id: number;
    title: string;
    description?: string;
    tags: string[];
    teacherName: string;
    teacherImg: string;
    nowStudents: number;
    maxStudents: number;
    courseImg: string;
  };
  isActive: boolean;
};

type CardData = HoverExpandCourseCardProps["cardData"];

const HoverExpandCourseCard = ({
  cardData,
  isActive,
}: HoverExpandCourseCardProps) => {
  const resultCard = isActive
    ? expandedCardContainer(cardData)
    : closedCardContainer(cardData);
  return resultCard;
};

const expandedCardContainer = (cardData: CardData) => {
  return (
    <div
      className="relative mx-[.8125rem] h-[21.3125rem] w-[54.375rem] cursor-pointer rounded-[1.875rem] bg-cover bg-center transition-all duration-600"
      style={{ backgroundImage: `url(${cardData.courseImg})` }}
    >
      <div className="absolute inset-0 overflow-hidden rounded-[1.875rem] bg-gradient-to-t from-black to-transparent px-[1.4375rem] py-[1.5rem]">
        <div className="flex h-full flex-col justify-between text-white">
          {cardTopContainer(cardData)}
          {cardBottomContainer(cardData)}
        </div>
      </div>
    </div>
  );
};

const closedCardContainer = (cardData: CardData) => {
  return (
    <div
      className="relative h-[21.3125rem] w-[5rem] rounded-[3.125rem] bg-cover bg-center opacity-60 transition-all duration-600"
      style={{ backgroundImage: `url(${cardData.courseImg})` }}
    >
      <div className="absolute inset-0 rounded-[3.125rem] bg-black/50 py-[1.375rem]">
        <div className="flex h-full flex-col items-center justify-end gap-[1rem]">
          <PersonIcon className="h-[1.5625rem] w-[1.625rem]" />
          <div className="typo-body-5 h-[1.5rem] text-white/70">
            {cardData.nowStudents}/{cardData.maxStudents}
          </div>
        </div>
      </div>
    </div>
  );
};

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
    <div className="flex w-full flex-row items-center justify-between gap-2">
      <div className="flex h-[4.8125rem] w-[13.75rem] flex-col gap-[.5rem]">
        <p className="typo-body-6 h-[1.3125rem] truncate text-white/60">
          {cardData.description}
        </p>
        <p className="typo-title-1 h-[3rem] truncate">{cardData.title}</p>
      </div>
      <div className="W-[32.0625rem] flex flex-col items-center gap-y-[.25rem]">
        <RoundTooltip
          text={`${cardData.maxStudents - cardData.nowStudents}명 남았어요`}
        />
        <div className="flex flex-row gap-[.9375rem]">
          <PersonIcon className="h-[1.5625rem] w-[1.625rem]" />
          <ProgressBar
            type={"ratio"}
            current={cardData.nowStudents}
            max={cardData.maxStudents}
          />
        </div>
      </div>
    </div>
  );
};

export default HoverExpandCourseCard;
