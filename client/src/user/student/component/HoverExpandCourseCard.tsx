import Chips from "@/shared/components/Chips";

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
      className={
        `h-[21.3125rem] transition-all duration-300 ${isActive ? `w-[54.375rem] rounded-[1.875rem] px-[1.4375rem] py-[1.5rem]` : "w-[5rem] rounded-[3.125rem] opacity-60 brightness-50"} cursor-pointer bg-cover bg-center`
      }
      style={{
        backgroundImage: `url(${cardData.courseImg})`,
      }}
    >
      {!isActive && (
        <div className="flex h-full flex-col justify-end p-4 text-white"></div>
      )}
      {isActive && (
        <div className="flex h-full flex-col justify-between text-white">
          {teacherTagContainer(cardData)}
          <p className="text-sm">{cardData.description}</p>
          <h3 className="text-lg font-bold">{cardData.title}</h3>

          <div className="mt-2 flex items-center">
            <span className="text-sm">{`${cardData.nowStudents} / ${cardData.maxStudents} students`}</span>
          </div>
          <div className="mt-auto flex items-center justify-between">
            <div className="text-sm">D-4</div>
          </div>
        </div>
      )}
    </div>
  );
}

const teacherTagContainer = (cardData: CardData) => {
  return (
    <div className="flex w-[824px] justify-between">
      <div className="flex items-center">
        <img
          src={cardData.teacherImg}
          alt="Teacher"
          className="mr-2 h-8 w-8 rounded-full"
        />
        <span className="text-sm">{cardData.teacherName}</span>
      </div>
      {cardData.tags?.map((tag, index) => (
        <Chips key={index} type="field" title={tag} interactive={false} />
      ))}
    </div>
  );
};
