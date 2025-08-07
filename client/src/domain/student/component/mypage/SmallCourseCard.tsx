import RightArrowIcon from "@/assets/icons/default/arrow-right.svg?react";

interface SmallCourseCardProps {
  subTitle: string;
  title: string;
  courseThumbnail: string;
  onClick?: () => void;
}
const SmallCourseCard = ({
  subTitle,
  title,
  courseThumbnail,
  onClick,
}: SmallCourseCardProps) => {
  return (
    <div
      className="relative flex h-[28.0956rem] w-[22.625rem] cursor-pointer items-end rounded-[1.25rem] transition-transform duration-300 ease-in-out hover:-translate-y-2.5 hover:shadow-main"
      style={{
        background: `url(${courseThumbnail})`,
      }}
      onClick={onClick}
    >
      <div className="absolute inset-0 flex flex-col items-end justify-between rounded-[1.25rem] bg-gradient-to-b from-transparent to-black/60 p-[1.9375rem] px-[1.25rem] py-[1.9238rem]">
        <RightArrowIcon className="h-[1.5rem] w-[1.5rem] text-white" />
        <div className="flex w-full flex-col justify-start">
          <span className="typo-body-0 text-white">{subTitle}</span>
          <span className="typo-title-1 text-white">{title}</span>
        </div>
      </div>
    </div>
  );
};

export default SmallCourseCard;
