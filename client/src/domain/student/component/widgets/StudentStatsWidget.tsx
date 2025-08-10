import * as React from "react";
import PeopleIcon from "@/assets/icons/gradient/people.svg?react";

type CountMetric = {
  label: string; // 예: "D+50 기준"
  value: string; // 예: "7강"
};

export interface StudentStatsWidgetProps {
  size: "small" | "large";
  title?: string; // 카드 제목
  // ⬇️ count형(스샷의 좌측 2개 카드 형태)
  countTop?: CountMetric; // 위 박스
  countBottom?: CountMetric; // 아래 박스
  countCaption?: string; // 하단 캡션 예: "평균 수강 강좌"

  // ⬇️ large 전용: 차트 카드 2종(라인, 레이더) 캡션
  lineChartCaption?: string; // 예: "평균 과제 제출률"
  radarChartCaption?: string; // 예: "평균 수강 연령"
}

const StudentStatsWidget = ({
  size,
  title = "전체 수강생 현황",
  countTop = { label: "D+50 기준", value: "7강" },
  countBottom = { label: "D+70 기준", value: "10강" },
  countCaption = "평균 수강 강좌",
  lineChartCaption = "평균 과제 제출률",
  radarChartCaption = "평균 수강 연령",
}: StudentStatsWidgetProps) => {
  if (size === "small") {
    return (
      <div className="flex h-[20.5rem] w-[20.5rem] flex-col gap-[1rem] rounded-[1.25rem] bg-white p-[1.5rem]">
        <Header title={title} />
        <FramedBox>
          <div className="flex h-full flex-col items-center justify-center gap-[0.5rem]">
            <MetricPill label={countTop.label} value={countTop.value} />
            <MetricPill label={countBottom.label} value={countBottom.value} />
            <span className="typo-label-3 mt-2 text-gray-600">
              {countCaption}
            </span>
          </div>
        </FramedBox>
      </div>
    );
  }

  if (size === "large") {
    return (
      <div className="flex h-[20.5rem] w-[63.5rem] flex-col gap-[1rem] rounded-[1.25rem] bg-white p-[1.5rem]">
        <Header title={title} />
        <div className="flex h-full w-full gap-[1rem]">
          <FramedBox>
            <div className="flex h-full flex-col items-center justify-center gap-[0.5rem]">
              <MetricPill label={countTop.label} value={countTop.value} />
              <MetricPill label={countBottom.label} value={countBottom.value} />
              <span className="typo-label-3 mt-2 text-gray-600">
                {countCaption}
              </span>
            </div>
          </FramedBox>
          <FramedBox>
            <span className="typo-label-3 text-center text-gray-700">
              {lineChartCaption}
            </span>
          </FramedBox>

          <FramedBox>
            <span className="typo-label-3 mt-3 mb-2 text-center text-gray-700">
              {radarChartCaption}
            </span>
          </FramedBox>
        </div>
      </div>
    );
  }

  return null;
};

export default StudentStatsWidget;

const Header = ({ title }: { title: string }) => {
  return (
    <div className="flex items-center gap-2">
      <span className="typo-title-5 text-gray-900">{title}</span>
      <PeopleIcon className="w-[1.6rem]" />
    </div>
  );
};

const FramedBox = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="flex-1 rounded-[1rem] bg-gradient-to-b from-[#26CAA9] to-[#0B50FD] p-[2px]">
      <div className="h-full rounded-[0.9rem] bg-white">{children}</div>
    </div>
  );
};

const MetricPill = ({ label, value }: { label: string; value: string }) => {
  return (
    <div className="flex w-[10rem] flex-col gap-1 rounded-[0.75rem] bg-gray-100 py-[1rem] text-center">
      <div className="typo-label-4 text-gray-500">{label}</div>
      <div className="typo-title-6 text-gray-900">{value}</div>
    </div>
  );
};
