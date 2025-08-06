import Button from "@/shared/components/Button";
import GradationChip from "@/shared/components/GradationChip";

interface Homework {
  Id: number;
  taskDescription: string;
  leftDays: number;
  submittedDate?: Date;
  submittedDescription?: string;
  feedback?: string;
}

interface HomeworkWidgetProps {
  size: "small" | "large";
  toDoHomework: Homework;
  submittedHomework: Homework;
}

const HomeworkWidget = ({
  size,
  toDoHomework,
  submittedHomework,
}: HomeworkWidgetProps) => {
  const submittedMonth = submittedHomework.submittedDate
    ? new Date(submittedHomework.submittedDate).getMonth() + 1
    : 0;

  const submittedDay = submittedHomework.submittedDate
    ? new Date(submittedHomework.submittedDate).getDate()
    : 0;

  if (size === "small") {
    return (
      <div className="flex h-[23.875rem] w-[23.875rem] flex-col rounded-[1.25rem] bg-white px-[1.5625rem] py-[1.625rem]">
        {/* 해야할 과제 */}
        {renderToDoHomework(toDoHomework)}
      </div>
    );
  }

  if (size === "large") {
    return (
      <div className="flex h-[23.875rem] w-[74.9375rem] cursor-default items-center justify-between gap-[2rem] rounded-[1.25rem] bg-white px-[2.1875rem] py-[1.125rem]">
        {/* 해야할 과제 */}
        <div className="flex flex-1">{renderToDoHomework(toDoHomework)}</div>

        <div className="h-[20.75rem] w-[.0625rem] bg-gray-300" />

        {/* 나의 과제 제출 영역 및 강사 피드백*/}
        <div className="flex flex-2 flex-col gap-[1.5rem]">
          {/* 제목 */}
          <div className="flex items-center gap-[.7188rem]">
            <span className="typo-title-6">
              {submittedMonth}월 {submittedDay}일 나의 과제
            </span>
            <GradationChip>
              <span className="typo-label-0 text-gray-600">제출 완료</span>
            </GradationChip>
          </div>

          {/* 제출 내용 및 강사 피드백*/}
          <p className="typo-label-0 h-[6rem] overflow-ellipsis whitespace-pre-line">
            {submittedHomework.submittedDescription}
          </p>
          <div className="h-[.0625rem] w-full bg-gray-300" />

          <span className="typo-label-0 h-[1.1875rem] text-gray-600">
            강사님의 피드백
          </span>
          <div className="h-[5.4375rem] w-full rounded-[.9375rem] bg-gray-100 px-[0.75rem] py-[.875rem]">
            <p className="typo-label-0 overflow-ellipsis whitespace-pre-line">
              {submittedHomework.feedback}
            </p>
          </div>
        </div>
      </div>
    );
  }
};

const renderToDoHomework = (toDoHomework: Homework) => {
  return (
    <div className="flex cursor-default flex-col gap-[1.5rem]">
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-[.625rem]">
          <span className="typo-title-3">과제</span>

          <GradationChip>
            <div className="flex items-center gap-[.25rem]">
              <span className="typo-label-0 text-gray-600">과제제출</span>
              <span className="typo-title-6">D-{toDoHomework.leftDays}</span>
            </div>
          </GradationChip>
        </div>
      </div>

      <div className="h-[.0625rem] w-full bg-gray-300" />

      {/* 과제 설명 or 제출 메시지 */}
      <span className="typo-label-0 text-gray-600">이번 과제</span>

      <span className="typo-body-5 h-[6rem] overflow-ellipsis text-gray-700">
        {toDoHomework.taskDescription}
      </span>
      <Button variant="outline">과제 제출하기</Button>
    </div>
  );
};

export default HomeworkWidget;
