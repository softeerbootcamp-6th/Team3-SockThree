interface RecommendedCourseListProps {
  listData: {
    teacherImg: string;
    teacherName: string;
    courseTitle: string;
    courseType: string;
    courseDays: string[];
    nowStudents: number;
    maxStudents: number;
  };
  onClick?: () => void;
}
const RecommendedCourseList = ({
  listData,
  onClick,
}: RecommendedCourseListProps) => {
  const courseStatus =
    listData.nowStudents >= listData.maxStudents ? "마감" : "모집중";

  return (
    <li
      className="flex h-[3.5rem] w-[51.6875rem] cursor-pointer items-center justify-between"
      onClick={onClick}
    >
      {/* 프로필 + 제목 */}
      <div className="flex items-center gap-2">
        {/* 상태 점 */}
        <span className="h-[.75rem] w-[.75rem] rounded-full bg-black" />
        {/* 프로필 이미지 */}
        <img
          src={listData.teacherImg}
          alt="Teacher"
          className="h-[1.8125rem] w-[1.8125rem] rounded-full object-cover"
        />
        {/* 이름 + 제목 */}
        <div className="flex flex-row items-center gap-[1rem]">
          <span className="typo-title-6 text-black">
            {listData.teacherName}
          </span>
          <span className="typo-title-5 w-[14.375rem] overflow-ellipsis whitespace-nowrap text-black">
            {listData.courseTitle}
          </span>
        </div>
      </div>

      {/* 타입 + 요일 + 인원 + 모집 상태*/}
      <div className="typo-body-5 flex w-[29.1875rem] flex-row items-center justify-between text-gray-600">
        <span>{listData.courseType}</span>
        <span>{listData.courseDays.join(" / ")}</span>
        <span>
          <span>
            {listData.nowStudents}/{listData.maxStudents}명
          </span>
        </span>
        <span className="typo-body-4 text-black">{courseStatus}</span>
      </div>
    </li>
  );
};

export default RecommendedCourseList;
