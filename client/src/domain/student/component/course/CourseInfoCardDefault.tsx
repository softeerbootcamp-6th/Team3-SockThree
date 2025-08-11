// CourseInfoCard.tsx (신청 전)
import CalendarIcon from "@/assets/icons/default/calendar.svg?react";
import StarIcon from "@/assets/icons/default/yellow-star.svg?react";
import Button from "@/shared/components/Button";

type UploadTime = { dayLabel: string; timeLabel: string };

type CourseInfoCardProps = {
  // 몇 번째 강좌인지 표시하는 플래그
  order?: number;
  title?: string;
  uploadTimes?: UploadTime[];
  periodLabel?: string;
  levelLabel?: string;
  pricePerHourLabel?: string; // 예: "30,000원"
  status: "recruiting" | "closed";
  onAction?: () => void;
  buttonText?: Partial<{ recruiting: string; closed: string }>;
};

export function CourseInfoCardDefault({
  order = 1,
  title = "강좌 제목",
  uploadTimes = [
    { dayLabel: "월", timeLabel: "10:00" },
    { dayLabel: "수", timeLabel: "14:00" },
    { dayLabel: "금", timeLabel: "16:00" },
  ],
  periodLabel = "2024.01.01 ~ 2024.02.28",
  levelLabel = "초급",
  pricePerHourLabel = "30,000원",
  status,
  onAction = () => {},
  buttonText,
}: CourseInfoCardProps) {
  const actionLabel =
    status === "closed"
      ? (buttonText?.recruiting ?? "이 강좌 신청하기")
      : (buttonText?.closed ?? "다음 기수 신청 알림 받기");

  return (
    <article className="flex h-fit w-[23.875rem] flex-col gap-[1rem] rounded-[1.25rem] bg-white p-6 shadow-sm">
      {/* 기수 라벨 */}
      <span className="flex w-fit items-center rounded-xl bg-gradient-to-r from-teal-500 to-blue-600 px-[1rem] py-[0.5rem] text-white">
        {order === 1 ? "신규" : `${order}기`}
      </span>
      {/* 강좌 제목 */}
      <h3 className="text-xl font-extrabold text-gray-900">{title}</h3>
      {/* 강좌 업로드 일시 */}
      <section className="flex flex-col gap-3">
        <div className="flex items-center gap-2 text-sm text-gray-500">
          <CalendarIcon className="w-[1.2rem]" />
          <span>강좌 업로드 일시</span>
        </div>
        <ul className="flex flex-col gap-2">
          {uploadTimes.map(({ dayLabel, timeLabel }, i) => (
            <li key={`${dayLabel}-${i}`} className="flex items-center gap-3">
              <span className="inline-flex h-10 min-w-10 items-center justify-center rounded-[0.6rem] border border-gray-300">
                {dayLabel}
              </span>
              <span className="text-sm text-gray-900">{timeLabel}</span>
            </li>
          ))}
        </ul>
      </section>
      {/* 가로선 */}
      <div className="h-px w-full bg-gray-200" />
      {/* 강좌 기간 */}
      <section className="flex flex-col gap-3">
        <div className="flex items-center gap-2 text-sm text-gray-500">
          <CalendarIcon className="w-[1.2rem]" />
          <span>강좌 기간</span>
        </div>
        <p className="typo-body-4 tracking-tight text-gray-800">
          {periodLabel}
        </p>
      </section>
      {/* 가로선 */}
      <div className="h-px w-full bg-gray-200" />
      {/* 난이도 */}
      <section className="flex flex-col gap-3">
        <div className="flex items-center gap-2 text-sm text-gray-500">
          <StarIcon className="w-[1.1rem] text-[#CFEFEF]" />
          <span>난이도</span>
        </div>
        <p className="typo-body-4 tracking-tight text-gray-800">{levelLabel}</p>
      </section>
      <div className="h-px w-full bg-gray-200" />
      {/* 수강료 */}
      <section className="flex flex-col gap-3">
        <div className="flex items-center gap-2 text-sm text-gray-500">
          <div className="flex h-[1.2rem] w-[1.2rem] content-center items-center justify-center rounded-full bg-[#CFEFEF] p-[0.2rem] text-[0.7rem] text-white">
            ₩
          </div>
          <span>수강료</span>
        </div>
        <div className="flex items-baseline gap-2">
          <span className="typo-body-4 tracking-tight text-gray-800">
            {pricePerHourLabel}
          </span>
          <span className="text-sm text-gray-500">/ 시간</span>
        </div>
      </section>
      <footer>
        <Button
          variant="default"
          size="md"
          className="typo-body-4"
          onClick={onAction}
        >
          {actionLabel}
        </Button>
      </footer>
    </article>
  );
}
