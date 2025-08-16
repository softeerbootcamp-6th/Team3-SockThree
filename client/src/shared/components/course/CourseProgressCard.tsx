import Button from "@/shared/components/Button";
import ProgressBar from "@/shared/components/ProgressBar";

interface CourseProgressCardProps {
  imageUrl?: string;
  teacherName?: string;
  title?: string;
  days?: string[];
  startTime?: string;
  endTime?: string;
  currentVideo?: number;
  maxVideo?: number;
  onClick?: () => void;
}

const CourseProgressCard = ({
  imageUrl = "https://cdn.pixabay.com/photo/2025/04/25/12/13/nature-9558835_1280.jpg",
  teacherName = "홍길동",
  title = "프론트엔드 개발자 양성 과정",
  days = ["월", "수", "금"],
  startTime = "10:00",
  endTime = "12:00",
  currentVideo = 3,
  maxVideo = 10,
  onClick = () => {},
}: CourseProgressCardProps) => {
  return (
    <div className="group relative flex h-[32.3125rem] w-[26.9375rem] flex-col gap-[.9375rem] overflow-clip rounded-[1.25rem] bg-white px-[1.375rem] py-[1.6563rem] transition-all duration-300 hover:-translate-y-[1.6875rem] hover:shadow-main">
      {/* 호버 시 카드 위(z)에 뜨는 버튼 */}
      <div className="absolute top-[19.0625rem] right-[2.3125rem] z-20 translate-y-2 opacity-0 transition-all duration-300 group-hover:translate-y-0 group-hover:opacity-100">
        <Button
          variant="outline"
          className="typo-label-1 h-[3.3125rem] w-[6.25rem] cursor-pointer"
          onClick={onClick}
        >
          상세 보기
        </Button>
      </div>

      {/* 이미지 영역 */}
      <div className="relative">
        <img
          src={imageUrl}
          alt={title}
          className="h-[19.75rem] w-full rounded-[1.25rem] object-cover"
        />
      </div>

      {/* 내용 영역 */}
      <div className="flex h-full flex-col gap-[.5625rem]">
        <span className="typo-label-2 whitespace-nowrap text-gray-500">
          {teacherName}
        </span>
        <span className="typo-title-3 truncate whitespace-nowrap">{title}</span>
        <div className="flex justify-between">
          <div className="flex items-center gap-[.9375rem]">
            <span className="typo-body-6">{days.join(" ")}</span>
            <span className="typo-body-6">
              {startTime} ~ {endTime}
            </span>
          </div>
        </div>
        <div className="mt-auto flex items-center justify-between">
          <span className="typo-label-1">진행률</span>
          <ProgressBar current={currentVideo} max={maxVideo} />
        </div>
      </div>
    </div>
  );
};

export default CourseProgressCard;
