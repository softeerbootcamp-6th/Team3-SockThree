type HoverExpandCourseCardProps = {
  cardData: {
    id: number;
    title: string;
    description?: string;
    tags?: string[];
    teacherId?: number;
    teacherImg?: string;
    nowStudents?: number;
    maxStudents?: number;
    courseImg?: string;
  };
  isActive: boolean;
};

export default function HoverExpandCourseCard({
  cardData,
  isActive,
}: HoverExpandCourseCardProps) {
  return (
    <div
      className={`h-[21.3125rem] rounded-[3.125rem] transition-all duration-300 ${isActive ? `w-[54.375rem] rounded-[1.875rem] px-[1.4375rem] py-[1.5rem]` : "w-[5rem] opacity-60 brightness-50"} cursor-pointer bg-cover bg-center`}
      style={{
        backgroundImage: `url(${cardData.courseImg})`,
      }}
    >
      {!isActive && (
        <div className="flex h-full flex-col justify-end p-4 text-white"></div>
      )}
      {isActive && (
        <div className="flex h-full flex-col justify-end p-4 text-black">
          <p className="text-sm">{cardData.description}</p>
          <h3 className="text-lg font-bold">{cardData.title}</h3>
          {cardData.tags?.map((tag, index) => (
            <span
              key={index}
              className="mr-2 inline-block rounded-full bg-gray-200 px-3 py-1 text-xs font-semibold text-gray-700"
            >
              {tag}
            </span>
          ))}
          <div className="mt-2 flex items-center">
            <img
              src={cardData.teacherImg}
              alt="Teacher"
              className="mr-2 h-8 w-8 rounded-full"
            />
            <span className="text-sm">{`Teacher ID: ${cardData.teacherId}`}</span>
          </div>
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
