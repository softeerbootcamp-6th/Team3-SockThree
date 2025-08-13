import InfoChip from "@/shared/components/InfoChip";

const CurrentCoursePage = () => {
  const studentName = "홍길동";
  let currentCourseLength = 3;

  const infoChipText =
    currentCourseLength === 0
      ? `${studentName}님은 아직 수강 중인 강의가 없어요`
      : `${studentName}님은 ${currentCourseLength}개의 강좌를 수강중이시네요!`;

  return (
    <div className="flex h-full w-full flex-col items-start gap-[1.25rem] overflow-auto pr-[3.0625rem] pb-[4.625rem] pl-[2.8125rem]">
      <div className="sticky top-0 flex w-full flex-col items-start gap-[1.25rem] bg-gradient-to-b from-bg from-85% to-transparent to-100% pt-[4.625rem] pb-[1.875rem]">
        <div className="flex w-full flex-row items-center gap-[2.25rem]">
          <span className="typo-title-0">현재 수강 중인 강좌</span>
          <InfoChip text={infoChipText} />
        </div>
        <span className="typo-title-6 text-gray-600">
          총 {currentCourseLength}건
        </span>
      </div>
      <div className="grid grid-cols-3 gap-[1.25rem]"></div>
    </div>
  );
};

export default CurrentCoursePage;
