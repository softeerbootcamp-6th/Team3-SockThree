import Button from "@/shared/components/Button";

interface Question {
  questionID: number;
  questionTitle: string;
}

interface QuestionWidgetProps {
  size: "small" | "large";
  resentQuestionList: Question[];
  frequentQuestionList: Question[];
}

export default function QuestionWidget({
  size,
  resentQuestionList,
  frequentQuestionList,
}: QuestionWidgetProps) {


  if (size === "small") {
    return (
      <div className="flex h-[23.875rem] w-[23.875rem] flex-col gap-[1rem] rounded-[1.25rem] bg-white px-[1.5625rem] py-[1.9063rem]">
        <div className="flex h-[2.1875rem] items-center justify-between">
          <span className="typo-title-3">질문있어요!</span>
        </div>
        <div className="h-[.0625rem] bg-gray-300" />
        <span className="typo-label-0 h-[1.1875rem] text-gray-600">
          총 {resentQuestionList.length}개
        </span>
        <ul className="flex h-[13.6875rem] flex-col gap-[.75rem] text-gray-700">
          {renderQuestions(frequentQuestionList)}
        </ul>
      </div>
    );
  }

  if (size === "large") {
    return (
      <div className="flex h-[23.875rem] w-[74.9375rem] items-center justify-center gap-[2.1875rem] rounded-[1.25rem] bg-white px-[1.875rem] py-[1.5625rem]">
        {/* 최근 질문 */}
        <div className="flex h-[19.125rem] w-full flex-col gap-[1rem]">
          <div className="flex h-[2.1875rem] items-center justify-between">
            <span className="typo-title-3">질문있어요!</span>
          </div>
          <div className="h-[.0625rem] bg-gray-300" />
          <span className="typo-label-0 h-[1.1875rem] text-gray-600">
            총 {resentQuestionList.length}개
          </span>
          <ul className="flex h-[13.6875rem] flex-col gap-[.75rem] text-gray-700">
            {renderQuestions(resentQuestionList)}
          </ul>
        </div>

        <div className="h-[20.75rem] w-[.0625rem] bg-gray-300" />

        {/* 자주 묻는 질문 */}
        <div className="flex h-[18.625rem] w-full flex-col gap-[1rem]">
          <span className="typo-title-6 h-[1.3125rem]">자주 묻는 질문</span>
          <div className="h-[.0625rem] bg-gray-300" />
          <p className="h-[1.1875rem] w-full text-gray-400">
            강사님이 고정해놓은 질문이에요
          </p>
          <ul className="flex h-[13.6875rem] w-full flex-col gap-[.75rem] text-gray-700">
            {renderQuestions(frequentQuestionList)}
          </ul>
        </div>

        <div className="h-[20.75rem] w-[.0625rem] bg-gray-300" />

        {/* 나도 질문하기 */}
        <div className="flex h-[18.625rem] w-full flex-col gap-[1rem]">
          <div className="flex items-center justify-between">
            <span className="typo-title-6 h-[1.3125rem]">나도 질문하기</span>
          </div>
          <div className="h-[.0625rem] bg-gray-300" />

          <textarea
            placeholder="질문을 입력해주세요."
            className="h-[11.5rem] resize-none rounded-[.9375rem] bg-gray-100 px-[1rem] py-[.75rem] placeholder:text-gray-500 focus:outline-none"
          />
          <Button variant={"outline"} size={"lgFlexible"} textSize={"title5"}>
            질문 등록하기
          </Button>
        </div>
      </div>
    );
  }

  return null;
}

const renderQuestions = (questionList: Question[]) => {
  return questionList.slice(0, 6).map(({ questionID, questionTitle }) => (
    <li
      key={questionID}
      className={"flex h-[21px] items-center justify-between"}
    >
      <div className="h-[.625rem] w-[.625rem] rounded-full bg-main-500" />
      <span className="typo-body-6 w-[16.375rem]">{questionTitle}</span>
      <button className="typo-label-3 cursor-pointer text-gray-500">{`보기 >`}</button>
    </li>
  ));
};