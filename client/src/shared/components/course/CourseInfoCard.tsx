import Chips from "@/shared/components/Chips";
import ProgressBar from "@/shared/components/ProgressBar";
import RoundTooltip from "@/shared/components/RoundTooltip";

import HeartIcon from "@/assets/icons/default/heart.svg?react";
import PersonIcon from "@/assets/icons/default/person.svg?react";

interface Course {
  id: number;
  title: string;
  description?: string;
  heartCount: number;
  tags?: string[];
  teacherName: string;
  nowStudents: number;
  maxStudents: number;
  courseImg: string;
}

interface CourseInfoCardProps {
  courseInfo: Course;
}

const CourseInfoCard = (cardData: CourseInfoCardProps) => {
  const { courseImg } = cardData.courseInfo;

  return (
    <div
      className="relative h-[21.3125rem] w-full rounded-[1.25rem] bg-cover bg-center"
      style={{ backgroundImage: `url(${courseImg})` }}
    >
      <div className="absolute inset-0 overflow-hidden rounded-[1.875rem] bg-gradient-to-t from-black to-transparent px-[1.5625rem] py-[1.25rem]">
        <div className="flex h-full flex-col justify-between text-white">
          {cardTopContainer(cardData.courseInfo)}
          {cardBottomContainer(cardData.courseInfo)}
        </div>
      </div>
    </div>
  );
};

const cardTopContainer = ({ tags, heartCount }: Course) => {
  return (
    <div className="flex w-full justify-between">
      <div className="flex flex-row items-center gap-[1.06rem]">
        <HeartIcon className="w-[1.75rem] text-main-500" />
        <span className="typo-label-1 p-[.625rem] text-main-500">
          {heartCount}
        </span>
      </div>
      <div className="flex items-center gap-[1.06rem]">
        {tags?.map((tag, index) => (
          <Chips key={index} type="field" title={tag} interactive={false} />
        ))}
      </div>
    </div>
  );
};

const cardBottomContainer = ({
  description,
  title,
  maxStudents,
  nowStudents,
}: Course) => {
  return (
    <div className="flex w-full flex-row items-center justify-between gap-2">
      <div className="flex flex-1/2 flex-col gap-[.5rem]">
        <p className="typo-label-1 truncate text-white/60">{description}</p>
        <p className="typo-title-0 truncate">{title}</p>
      </div>
      <div className="W-[32.0625rem] flex flex-col items-center gap-y-[.25rem]">
        <RoundTooltip
          text={
            maxStudents - nowStudents !== 0
              ? `${maxStudents - nowStudents}명 남았어요`
              : "정원이 모두 찼어요"
          }
        />
        <div className="flex flex-row gap-[.9375rem]">
          <PersonIcon className="w-[1.625rem] text-white" />
          <ProgressBar
            type="ratio"
            current={nowStudents}
            max={maxStudents}
            color="mono"
          />
        </div>
      </div>
    </div>
  );
};

export default CourseInfoCard;
