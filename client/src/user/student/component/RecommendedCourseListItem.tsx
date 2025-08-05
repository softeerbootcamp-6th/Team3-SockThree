interface RecommendedCourseListItemProps {
  listItemData: {
    teacherImg: string;
    teacherName: string;
    courseTitle: string;
    courseType: string;
    courseDays: string[];
    nowStudents: number;
    maxStudents: number;
    courseStatus?: string; // 확인: status 삭제될 수도 있음
  };
  onClick?: () => void;
}
const RecommendedCourseListItem = ({
  listItemData,
  onClick,
}: RecommendedCourseListItemProps) => {
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
          src={listItemData.teacherImg}
          alt="Teacher"
          className="h-[1.8125rem] w-[1.8125rem] rounded-full object-cover"
        />
        {/* 이름 + 제목 */}
        <div className="flex flex-row items-center gap-[1rem]">
          <span className="typo-title-6 text-black">
            {listItemData.teacherName}
          </span>
          <span className="typo-title-5 w-[14.375rem] overflow-ellipsis whitespace-nowrap text-black">
            {listItemData.courseTitle}
          </span>
        </div>
      </div>

      {/* 타입 + 요일 + 인원 + 모집 상태*/}
      <div className="typo-body-5 flex w-[29.1875rem] flex-row items-center justify-between text-gray-600">
        <span>{listItemData.courseType}</span>
        <span>{listItemData.courseDays.join(" / ")}</span>
        <span>
          <span>
            {listItemData.nowStudents}/{listItemData.maxStudents}명
          </span>
        </span>
        <span className="typo-body-4 text-black">
          {listItemData.courseStatus}
        </span>
      </div>
    </li>
  );
};

export default RecommendedCourseListItem;
