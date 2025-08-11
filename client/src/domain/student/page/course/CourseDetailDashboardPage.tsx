import StudentStatsWidget from "@/domain/student/component/widgets/StudentStatsWidget";
import InstructorInfoWidget from "@/domain/student/component/widgets/InstructorInfoWidget";

const CourseDetailDashboardPage = () => {
  return (
    <div className="flex w-full flex-col gap-[1rem]">
      <div className="flex flex-row gap-[1rem]">
        <StudentStatsWidget
          size="large"
          ageData={[
            { subject: "50세", male: 10, female: 15, fullMark: 40 },
            { subject: "55세", male: 18, female: 20, fullMark: 40 },
            { subject: "60세", male: 20, female: 8, fullMark: 40 },
            { subject: "65세", male: 15, female: 10, fullMark: 40 },
            { subject: "70세", male: 12, female: 2, fullMark: 40 },
            { subject: "75세", male: 5, female: 1, fullMark: 40 },
          ]}
          homeworkData={[
            { name: "2회", value: 100 },
            { name: "3회", value: 50 },
            { name: "4회", value: 60 },
            { name: "5회", value: 60 },
            { name: "6회", value: 20 },
          ]}
        />
        <InstructorInfoWidget size="small" />
      </div>
      <div className="flex flex-row gap-[1rem]">
        <InstructorInfoWidget size="large" />
        <StudentStatsWidget size="small" />
      </div>
      <div className="flex flex-row gap-[1rem]">
        <StudentStatsWidget size="small" />
        <InstructorInfoWidget size="large" />
      </div>
    </div>
  );
};

export default CourseDetailDashboardPage;
