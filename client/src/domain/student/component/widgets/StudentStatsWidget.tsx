import * as React from "react";
import PeopleIcon from "@/assets/icons/gradient/people.svg?react";
import {
  AreaChart,
  Area,
  XAxis,
  CartesianGrid,
  ResponsiveContainer,
  Radar,
  RadarChart,
  PolarAngleAxis,
  PolarGrid,
} from "recharts";

export type CountMetric = {
  label: string;
  value: string;
};

type SmallProps = {
  size: "small";
  countTop?: CountMetric;
  countBottom?: CountMetric;
  ageData?: never;
  homeworkData?: never;
};

type LargeProps = {
  size: "large";
  countTop?: CountMetric;
  countBottom?: CountMetric;
  ageData: {
    subject: string;
    male: number;
    female: number;
    fullMark: number;
  }[];
  homeworkData: { name: string; value: number }[];
};

export type StudentStatsWidgetProps = SmallProps | LargeProps;

const StudentStatsWidget = ({
  size,
  countTop = { label: "D+50 기준", value: "7강" },
  countBottom = { label: "D+70 기준", value: "10강" },
  homeworkData = [
    { name: "2회", value: 100 },
    { name: "3회", value: 50 },
    { name: "4회", value: 60 },
    { name: "5회", value: 60 },
    { name: "6회", value: 20 },
  ],
  ageData = [
    { subject: "50세", male: 10, female: 15, fullMark: 40 },
    { subject: "55세", male: 18, female: 20, fullMark: 40 },
    { subject: "60세", male: 20, female: 8, fullMark: 40 },
    { subject: "65세", male: 15, female: 10, fullMark: 40 },
    { subject: "70세", male: 12, female: 2, fullMark: 40 },
    { subject: "75세", male: 5, female: 1, fullMark: 40 },
  ],
}: StudentStatsWidgetProps) => {
  const title = "전체 수강생 현황";
  const lineChartCaption = "평균 과제 제출률";
  const radarChartCaption = "평균 수강 연령";

  if (size === "small") {
    return (
      <div className="flex h-[20.5rem] w-[20.5rem] flex-col gap-[1rem] rounded-[1.25rem] bg-white p-[1.5rem]">
        <Header title={title} />
        <FramedBox>
          <div className="flex h-full flex-col items-center justify-center gap-[0.5rem]">
            <MetricPill label={countTop.label} value={countTop.value} />
            <MetricPill label={countBottom.label} value={countBottom.value} />
            <span className="typo-label-3 mt-2 text-gray-600">
              평균 수강 강좌
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
            <div className="flex h-full flex-col items-center justify-between py-[1.5rem]">
              <div className="flex flex-col gap-[0.5rem]">
                <MetricPill label={countTop.label} value={countTop.value} />
                <MetricPill
                  label={countBottom.label}
                  value={countBottom.value}
                />
              </div>
              <span className="typo-label-3 mt-2 text-gray-900">
                평균 수강 강좌
              </span>
            </div>
          </FramedBox>
          <FramedBox>
            <div className="flex h-full w-full flex-col items-center justify-between pb-[1.5rem]">
              <ResponsiveContainer width="100%" height="100%">
                <AreaChart
                  data={homeworkData}
                  margin={{ top: 30, left: 30, right: 30, bottom: 10 }}
                >
                  <CartesianGrid vertical horizontal={false} />
                  <XAxis dataKey="name" tickLine={false} />
                  <Area
                    type="monotone"
                    dataKey="value"
                    stroke="#00bfae"
                    fill="url(#colorUv)"
                    dot={{
                      r: 5,
                      stroke: "#00bfae",
                      strokeWidth: 2,
                      fill: "#fff",
                    }}
                    activeDot={(props) => {
                      const { cx, cy, payload } = props;
                      return (
                        <g>
                          <circle
                            cx={cx}
                            cy={cy}
                            r={5}
                            stroke="#00bfae"
                            strokeWidth={2}
                            fill="#fff"
                          />
                          <text
                            className="typo-label-3"
                            fill="var(--color-gray-600)"
                            x={cx}
                            y={cy - 10}
                          >
                            {payload.value}%
                          </text>
                        </g>
                      );
                    }}
                  ></Area>
                  <defs>
                    <linearGradient id="colorUv" x1="0" y1="0" x2="0" y2="1">
                      <stop offset="0%" stopColor="#00bfae" stopOpacity={0.7} />
                      <stop
                        offset="100%"
                        stopColor="#00bfae"
                        stopOpacity={0.3}
                      />
                    </linearGradient>
                  </defs>
                </AreaChart>
              </ResponsiveContainer>
              <span className="typo-label-3 text-center text-gray-700">
                {lineChartCaption}
              </span>
            </div>
          </FramedBox>

          <FramedBox>
            <div className="flex h-full w-full flex-col items-center justify-between pb-[1rem]">
              <ResponsiveContainer width="100%" height="100%">
                <RadarChart
                  cx="50%"
                  cy="57%"
                  outerRadius="75%"
                  data={ageData}
                  startAngle={60}
                  endAngle={-300}
                >
                  <PolarGrid radialLines={false} />
                  <PolarAngleAxis dataKey="subject" />
                  <Radar
                    name="남성"
                    dataKey="male"
                    stroke="#00bfae"
                    fill="url(#maleGradient)"
                    fillOpacity={0.6}
                  />
                  <Radar
                    name="여성"
                    dataKey="female"
                    stroke="#ff6b81"
                    fill="url(#femaleGradient)"
                    fillOpacity={0.6}
                  />
                  <defs>
                    <linearGradient
                      id="maleGradient"
                      x1="0"
                      y1="0"
                      x2="0"
                      y2="1"
                    >
                      <stop offset="0%" stopColor="#00bfae" stopOpacity={0.7} />
                      <stop
                        offset="100%"
                        stopColor="#00bfae"
                        stopOpacity={0.3}
                      />
                    </linearGradient>
                    <linearGradient
                      id="femaleGradient"
                      x1="0"
                      y1="0"
                      x2="0"
                      y2="1"
                    >
                      <stop offset="0%" stopColor="#ff6b81" stopOpacity={0.7} />
                      <stop
                        offset="100%"
                        stopColor="#ff6b81"
                        stopOpacity={0.3}
                      />
                    </linearGradient>
                  </defs>
                </RadarChart>
              </ResponsiveContainer>

              <span className="typo-label-3 mt-3 mb-2 text-center text-gray-700">
                {radarChartCaption}
              </span>
            </div>
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
      <div className="flex h-full flex-col items-center justify-center rounded-[0.9rem] bg-white">
        {children}
      </div>
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
