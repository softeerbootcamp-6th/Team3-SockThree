import CourseDetailCard from "@/shared/components/course/CourseDetailCard";
import { useNavigate } from "react-router";

interface CourseDetailCardProps {
  courseId: number;
  imageUrl?: string;
  teacherName?: string;
  title?: string;
  days?: string[];
  startTime?: string;
  endTime?: string;
  rating?: number;
  reviews?: number;
  price?: number;
  heartButtonInvisible?: boolean;
  defaultFavorite?: boolean;
}

interface CourseCardContainerProps {
  cardItems?: CourseDetailCardProps[];
}

const CourseCardContainer = ({ cardItems }: CourseCardContainerProps) => {
  const navigate = useNavigate();

  const handleCardClick = (courseId: number) => {
    navigate(`/instructor/course/${courseId}/dashboard`);
  };

  return (
    <div className="flex w-full flex-col gap-[2.4375rem]">
      <span className="typo-title-4 pt-[1.25rem] text-gray-600">
        총 {cardItems?.length}건
      </span>

      {cardItems && cardItems.length > 0 ? (
        <div className="flex w-full">
          <div className="grid grid-cols-3 gap-[2.125rem]">
            {cardItems.map((item) => (
              <CourseDetailCard
                key={item.courseId}
                imageUrl={item.imageUrl}
                teacherName={item.teacherName}
                title={item.title}
                days={item.days}
                startTime={item.startTime}
                endTime={item.endTime}
                rating={item.rating}
                reviews={item.reviews}
                price={item.price}
                heartButtonInvisible={item.heartButtonInvisible}
                defaultFavorite={item.defaultFavorite}
                onClick={() => handleCardClick(item.courseId)}
              />
            ))}
          </div>
        </div>
      ) : (
        <div className="flex h-[31.25rem] items-center justify-center text-gray-500">
          표시할 강좌가 없습니다.
        </div>
      )}
    </div>
  );
};

export default CourseCardContainer;
