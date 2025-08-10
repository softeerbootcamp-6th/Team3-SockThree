import AvatarSrc from "@/assets/logo/default-avatar.svg";
import StarIcon from "@/assets/icons/default/yellow-star.svg?react";
import GradationChip from "@/shared/components/GradationChip";

type Bullet = { id: number; text: string };

type Stat =
  | { key: "totalClasses"; label: "총 강좌 진행 수"; value: string }
  | { key: "students"; label: "참여 학생 수"; value: string }
  | { key: "matches"; label: "매칭 성사"; value: string }
  | { key: "certs"; label: "관련 자격증"; value: string }
  | { key: "reviews"; label: "리뷰"; value: string }
  | { key: "avgScore"; label: "평균 강의 평가"; value: string };

export interface InstructorInfoWidgetProps {
  size: "small" | "large";
  avatarUrl?: string | null;
  name?: string;
  title?: string; // ex) "전 골프선수"
  trustPercent?: number; // ex) 72
  rating?: number; // ex) 4.9
  tagline?: string; // ex) “골프의 모든 것을 알려드려요!”
  leftBullets?: Bullet[]; // 작은 카드/큰 카드 좌측 "강사 정보"
  rightBullets?: Bullet[]; // 큰 카드 전용 우측 "강사 정보"
  stats?: Stat[]; // 큰 카드 하단 지표
}

const InstructorWidget = ({
  size = "small",
  avatarUrl,
  name = "김골프",
  title = "전 골프선수",
  trustPercent = 72,
  rating = 3.5,
  tagline = "골프의 모든 것을 알려드려요!",
  leftBullets = [
    { id: 1, text: "전국 골프대회 우승 경력" },
    { id: 2, text: "10년 이상 골프 강의 경력" },
    { id: 3, text: "골프 관련 자격증 보유" },
  ],
  rightBullets = [
    { id: 4, text: "골프 클럽 맞춤 추천" },
    { id: 5, text: "스윙 교정 전문" },
    { id: 6, text: "골프 퍼팅 기술 향상" },
  ],
  stats = [
    { key: "totalClasses", label: "총 강좌 진행 수", value: "15" },
    { key: "students", label: "참여 학생 수", value: "120" },
    { key: "matches", label: "매칭 성사", value: "30" },
    { key: "certs", label: "관련 자격증", value: "5" },
    { key: "reviews", label: "리뷰", value: "200" },
    { key: "avgScore", label: "평균 강의 평가", value: "4.8" },
  ],
}: InstructorInfoWidgetProps) => {
  if (size === "small") {
    return (
      <div className="flex min-h-[20rem] min-w-[20rem] flex-col gap-[1rem] rounded-[1.25rem] bg-white px-[1.5625rem] py-[1.9063rem]">
        {/* 프로필 영역 */}
        <div className="flex items-center gap-[.75rem]">
          <Avatar src={avatarUrl} />
          <div className="flex flex-col">
            <div className="flex items-end gap-[.5rem]">
              <span className="typo-title-4 text-gray-900">{name}</span>
              {title ? (
                <span className="typo-label-3 text-gray-500">{title}</span>
              ) : null}
            </div>
          </div>
        </div>
        {/*인기도 영역*/}
        <div className="flex items-center justify-start gap-[.75rem]">
          <GradationChip>
            <div className="typo-label-4 flex items-center gap-0.5">
              <span className="text-gray-600">신뢰도</span>
              <span className="typo-label-3">{trustPercent}%</span>
            </div>
          </GradationChip>
          <div className="flex items-end justify-center gap-1">
            <StarIcon className="w-[1rem]" />
            <span className="typo-label-0">{rating}</span>
          </div>
        </div>
        {/* 태그라인 */}
        <p className="typo-body-6 text-gray-700">{tagline}</p>
        {/* 구분선 */}
        <div className="h-px bg-gray-300" />
        {/* 강사 정보 */}
        <div className="flex flex-col gap-[1rem]">
          <span className="typo-label-2 text-gray-500">강사 정보</span>
          <ul className="flex flex-col gap-[.5rem] text-gray-700">
            {renderBullets(leftBullets)}
          </ul>
        </div>
      </div>
    );
  }

  if (size === "large") {
    return (
      <div className="flex h-[23.875rem] w-[74.9375rem] flex-col rounded-[1.25rem] bg-white px-[2rem] py-[1.75rem]">
        {/* 상단 영역 */}
        <div className="flex flex-1 items-start gap-[2rem]">
          {/* 프로필 영역 */}
          <section className="flex w-[40%] items-start gap-[1.25rem]">
            <Avatar src={avatarUrl} size="lg" />
            <div className="flex flex-col">
              <div className="flex items-center gap-[.5rem]">
                <span className="typo-title-3 text-gray-900">{name}</span>
                {title ? (
                  <span className="typo-label-2 text-gray-500">{title}</span>
                ) : null}
              </div>

              <div className="mt-[.75rem] flex items-center gap-[.75rem]">
                <div className="typo-label-3 rounded-full bg-main-100 px-[.75rem] py-[.375rem] text-main-700">
                  신뢰도 {trustPercent}%
                </div>
                <div className="flex items-center gap-[.375rem]">
                  <span className="typo-label-3">★</span>
                  <span className="typo-label-3">{rating}</span>
                </div>
              </div>

              {tagline ? (
                <p className="typo-body-6 mt-[.875rem] text-gray-700">
                  “{tagline}”
                </p>
              ) : null}
            </div>
          </section>

          {/* 세로 구분선 */}
          <div className="h-[6.5rem] w-px self-start bg-gray-300" />

          {/* 강사 정보(우측 2열) */}
          <section className="flex w-[60%] flex-col">
            <span className="typo-label-2 text-gray-500">강사 정보</span>
            <ul className="mt-[.75rem] grid grid-cols-2 gap-x-[3rem] gap-y-[.75rem] text-gray-700">
              {renderBullets([...leftBullets, ...rightBullets])}
            </ul>
          </section>
        </div>

        {/* 하단 구분선 */}
        <div className="mt-[1.25rem] h-px w-full bg-gray-300" />

        {/* 하단 지표 6칸 */}
        {stats.length > 0 ? (
          <section className="mt-[1rem] grid grid-cols-6 gap-[.75rem]">
            {stats.map((s) => (
              <StatBox key={s.key} label={s.label} value={s.value} />
            ))}
          </section>
        ) : null}
      </div>
    );
  }

  return null;
};

const Avatar = ({
  src = AvatarSrc,
  size = "md",
}: {
  src?: string | null;
  size?: "md" | "lg";
}) => {
  const box = size === "lg" ? "h-[4.5rem] w-[4.5rem]" : "h-[3.5rem] w-[3.5rem]";
  return src ? (
    <img
      src={src}
      alt="avatar"
      className={`${box} rounded-full bg-white object-cover`}
    />
  ) : (
    <></>
  );
};

const StatBox = ({ label, value }: { label: string; value: string }) => (
  <div className="flex h-[4.25rem] flex-col items-center justify-center rounded-[.75rem] bg-gray-50">
    <span className="typo-label-3 text-gray-500">{label}</span>
    <span className="typo-title-6 text-gray-900">{value}</span>
  </div>
);

function renderBullets(list: Bullet[]) {
  return list.map((item) => (
    <li key={item.id} className="flex items-center gap-[.5rem]">
      <span className="h-[.375rem] w-[.375rem] rounded-full bg-main-500" />
      <span className="typo-body-6">{item.text}</span>
    </li>
  ));
}

export default InstructorWidget;
