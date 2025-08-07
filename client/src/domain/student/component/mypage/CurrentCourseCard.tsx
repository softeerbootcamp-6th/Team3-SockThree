import Button from "@/shared/components/Button";

interface CurrentCourseCardProps {
  currentCourseLength: number;
  currentCourseThumbnail: string;
}

const CurrentCourseCard = ({
  currentCourseLength,
  currentCourseThumbnail,
}: CurrentCourseCardProps) => {
  return (
    <div className="flex h-[36.9375rem] w-[43.9375rem] flex-col gap-[1.0625rem]">
      <div
        className="relative flex h-[30.3125rem] w-full rounded-[1.25rem] bg-cover bg-center object-cover"
        style={{ backgroundImage: `url(${currentCourseThumbnail})` }}
      >
        <div className="absolute inset-0 flex items-end rounded-[1.25rem] bg-black/50 p-[1.9375rem]">
          <div className="flex flex-col">
            <span className="typo-body-0 text-white">
              수강 중 강좌 {currentCourseLength}개
            </span>

            <span className="typo-title-1 text-white">
              내가 수강 중인 강좌 목록이에요
            </span>
          </div>
        </div>
      </div>
      <Button variant={"outline"} className="flex w-full">
        나의 강좌 목록
      </Button>
    </div>
  );
};

export default CurrentCourseCard;
