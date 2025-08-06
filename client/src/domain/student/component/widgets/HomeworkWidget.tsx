import Button from "@/shared/components/Button";

interface AssignmentWidgetProps {
  size: "1x1" | "3x1";
  status: "pending" | "submitted";
  dueText?: string; // e.g., D-4
  isToggleOn?: boolean;
  taskDescription: string;
  uploadMessage?: string;
  feedbackMessage?: string;
}

export default function AssignmentWidget({
  size,
  status,
  dueText = "D-4",
  isToggleOn = false,
  taskDescription,
  uploadMessage,
  feedbackMessage,
}: AssignmentWidgetProps) {
  if (size === "1x1") {
    return (
      <div className="flex h-[23.875rem] w-[23.875rem] flex-col justify-between rounded-[1.25rem] bg-white px-[1.5625rem] py-[1.9063rem] shadow-sm">
        {/* Header */}
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-[0.5rem]">
            <span className="typo-title-3">과제</span>
            {status === "pending" ? (
              <span className="bg-sub-color rounded-[0.625rem] px-[0.625rem] py-[0.125rem] text-[0.75rem] font-semibold text-white">
                과제제출 {dueText}
              </span>
            ) : (
              <span className="rounded-[0.625rem] bg-gray-100 px-[0.625rem] py-[0.125rem] text-[0.75rem] font-semibold text-gray-500">
                제출 완료
              </span>
            )}
          </div>

          <div
            className={`h-[1.5rem] w-[2.75rem] rounded-full border ${
              isToggleOn ? "bg-main-500" : "bg-gray-200"
            } flex items-center px-[.125rem]`}
          >
            <div
              className={`h-[1.25rem] w-[1.25rem] rounded-full bg-white shadow-md transition-all ${
                isToggleOn ? "translate-x-[1.25rem]" : "translate-x-0"
              }`}
            />
          </div>
        </div>

        {/* 과제 설명 or 제출 메시지 */}
        {status === "pending" ? (
          <div className="typo-body-6 leading-[1.25rem] text-gray-700">
            {taskDescription}
          </div>
        ) : (
          <div className="flex flex-col gap-[0.75rem] text-gray-700">
            <div className="typo-body-5">{uploadMessage}</div>
            {feedbackMessage && (
              <div className="rounded-[0.75rem] bg-gray-50 px-[0.75rem] py-[0.625rem] text-gray-800 shadow-inner">
                <div className="typo-body-6">{feedbackMessage}</div>
              </div>
            )}
          </div>
        )}

        {/* 버튼 */}
        {status === "pending" && (
          <Button
            variant="outline"
            size="lgFlexible"
            textSize="title5"
            className="w-full"
          >
            이 과제 확인하기
          </Button>
        )}
      </div>
    );
  }

  if (size === "3x1") {
    return (
      <div className="flex h-[23.875rem] w-[74.9375rem] items-center justify-between gap-[2rem] rounded-[1.25rem] bg-white px-[1.875rem] py-[1.5625rem] shadow-sm">
        {/* 나의 과제 제출 영역 */}
        <div className="flex w-full flex-col gap-[1rem]">
          <div className="flex items-center justify-between">
            <span className="typo-title-3">{`7월 25일 나의 과제`}</span>
            <span className="rounded-[0.625rem] bg-gray-100 px-[0.625rem] py-[0.125rem] text-[0.75rem] font-semibold text-gray-500">
              제출 완료
            </span>
          </div>
          <div className="typo-body-5 text-gray-700">{uploadMessage}</div>
        </div>

        <div className="h-[20.75rem] w-[.0625rem] bg-gray-300" />

        {/* 강사 피드백 */}
        <div className="flex w-full flex-col gap-[1rem]">
          <span className="typo-title-6 h-[1.3125rem] text-gray-800">
            강사님의 피드백
          </span>
          <div className="rounded-[0.75rem] bg-gray-50 px-[0.75rem] py-[0.625rem] text-gray-800 shadow-inner">
            <div className="typo-body-6 whitespace-pre-line">
              {feedbackMessage}
            </div>
          </div>
        </div>

        <div className="h-[20.75rem] w-[.0625rem] bg-gray-300" />

        {/* 토글 영역 (단순 뷰용) */}
        <div className="flex flex-col items-end justify-start gap-[1rem]">
          <div
            className={`h-[1.5rem] w-[2.75rem] rounded-full border ${
              isToggleOn ? "bg-main-500" : "bg-gray-200"
            } flex items-center px-[.125rem]`}
          >
            <div
              className={`h-[1.25rem] w-[1.25rem] rounded-full bg-white shadow-md transition-all ${
                isToggleOn ? "translate-x-[1.25rem]" : "translate-x-0"
              }`}
            />
          </div>
        </div>
      </div>
    );
  }

  return null;
}
