// CourseInfoCardAttend.tsx (신청 후)
import { PieChart, Pie, Cell, Label } from "recharts";

type Assignment = {
  title: string;
  dueLabel: string; // 예: "~25.07.31 까지"
  submitted?: boolean; // true면 '제출 완료' 표시
};

type CourseInfoCardAttendProps = {
  title?: string; // 카드 타이틀
  progress?: number; // 0~100 (내 진도율)
  totalHoursLabel?: string; // 예: "121시간"
  lectureCountLabel?: string; // 예: "강의 9개"
  assignment?: Assignment;
  dday?: number; // 남은 기간 D - n
};

type DonutProgressChartProps = {
  value: number; // 0~100
  size?: number; // 정사각형 크기(px)
  thickness?: number; // 도넛 두께(px)
};

export function ProgressDonut({
  value,
  size = 152,
  thickness = 24,
}: DonutProgressChartProps) {
  const clamped = Math.max(0, Math.min(100, value));
  const data = [
    { name: "progress", value: clamped },
    { name: "rest", value: 100 - clamped },
  ];

  const outer = size / 2;
  const inner = outer - thickness;

  return (
    <PieChart width={size} height={size}>
      <defs>
        <linearGradient id="donutGradient" x1="0" y1="0" x2="0" y2="1">
          <stop offset="0%" stopColor="#20C997" />
          <stop offset="100%" stopColor="#0B50FD" />
        </linearGradient>
      </defs>

      <Pie
        data={data}
        dataKey="value"
        startAngle={90} // 12시 방향 시작
        endAngle={-270} // 시계 방향으로 진행
        innerRadius={inner}
        outerRadius={outer}
        stroke="none"
        paddingAngle={0}
        cornerRadius={0}
      >
        {/* 진행 구간 = 그라디언트 */}
        <Cell fill="url(#donutGradient)" />
        {/* 나머지 구간 = 회색 */}
        <Cell fill="#E1E6EC" />

        {/* 가운데 퍼센트 라벨 */}
        <Label
          value={`${clamped}%`}
          position="center"
          className="fill-gray-800 text-xl font-bold"
        />
      </Pie>
    </PieChart>
  );
}

export function CourseInfoCardAttend({
  title = "나의 강좌 현황",
  progress = 52,
  totalHoursLabel = "121시간",
  lectureCountLabel = "강의 9개",
  assignment = {
    title: "골프 스윙 20분 연습하기",
    dueLabel: "~25.07.31 까지",
    submitted: true,
  },
  dday = 21,
}: CourseInfoCardAttendProps) {
  return (
    <article className="flex h-fit w-[23.875rem] flex-col gap-[1rem] rounded-[1.25rem] bg-white p-6 shadow-sm">
      {/* 타이틀 */}
      <h3 className="text-xl font-extrabold text-gray-900">{title}</h3>

      {/* 내 진도율 */}
      <section className="flex flex-col gap-3">
        <span className="text-sm text-gray-500">내 진도율</span>
        <div className="flex w-full items-center justify-center">
          <ProgressDonut value={progress} />
        </div>
      </section>

      {/* 구분선 */}
      <div className="h-px w-full bg-gray-200" />

      {/* 학습 시간 */}
      <section className="flex items-center justify-between">
        <span className="text-sm text-gray-500">학습 시간</span>
        <span className="typo-label-0 text-gray-900">
          {totalHoursLabel} / {lectureCountLabel}
        </span>
      </section>

      {/* 구분선 */}
      <div className="h-px w-full bg-gray-200" />

      {/* 이번주 과제 제출 */}
      <section className="flex flex-col gap-2">
        <div className="flex items-center justify-between text-sm text-gray-500">
          <span>이번주 과제 제출</span>
          <span>{assignment.dueLabel}</span>
        </div>

        <div className="flex flex-1 items-center justify-between">
          <div className="flex items-center gap-2">
            <span className="h-[0.5rem] w-[0.5rem] rounded-full bg-main-400" />
            <span className="typo-label-0 line-clamp-1 max-w-[7rem] text-gray-900">
              {assignment.title}
            </span>
          </div>
          <span
            className={
              assignment.submitted
                ? "typo-label-3 text-main-300"
                : "typo-label-3 text-gray-800"
            }
          >
            {assignment.submitted ? "제출 완료" : "미제출"}
          </span>
        </div>
      </section>

      {/* 남은 기간 배너 */}
      <div className="typo-body-4 flex items-center justify-center rounded-xl bg-gray-100 p-[1rem] text-gray-500">
        강좌 남은 기간 D - {dday}
      </div>
    </article>
  );
}

export default CourseInfoCardAttend;
