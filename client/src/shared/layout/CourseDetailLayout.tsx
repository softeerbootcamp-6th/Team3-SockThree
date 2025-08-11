import CourseInfoCard from "@/shared/components/course/CourseInfoCard";
import { CourseInfoCardDefault } from "@/domain/student/component/course/CourseInfoCardDefault";
import { Outlet, useParams } from "react-router-dom";
import CourseDetailTabBar from "@/shared/components/course/CourseDetailTabBar";

const CourseDetailLayout = () => {
  const { courseId } = useParams();
  console.log(courseId);

  return (
    <main className="mx-auto flex max-w-[1720px] min-w-[1280px] flex-col items-center justify-center">
      <div className="flex w-full flex-row gap-[1rem] py-6">
        {/* 메인 영역: 남은 폭 채우기 */}
        <section className="flex flex-1 flex-col gap-[1rem]">
          <CourseInfoCard
            courseInfo={{
              id: 1,
              title: "프론트엔드 개발자 양성과정",
              description:
                "이 과정은 프론트엔드 개발의 기초부터 심화까지 다룹니다.",
              heartCount: 129,
              tags: ["프론트엔드", "웹 개발", "JavaScript"],
              teacherName: "홍길동",
              nowStudents: 30,
              maxStudents: 30,
              courseImg: "",
            }}
          />
          {/* 강좌 소개 박스 */}
          <div className="min-h-[10rem] w-full rounded-[1.5rem] bg-white shadow-bg">
            <div className="flex flex-col gap-[0.5rem] p-[2rem]">
              <h3 className="typo-body-4">강좌 소개</h3>
              <span className="line-clamp-5 max-h-[5rem] min-h-[4rem]">
                description
              </span>
            </div>
          </div>
          <CourseDetailTabBar isEnrolled={true} />
          <div className="flex-1">
            <Outlet />
          </div>
        </section>

        <aside>
          <div className="sticky top-[6rem]">
            <CourseInfoCardDefault status="recruiting" />
          </div>
        </aside>
      </div>
    </main>
  );
};

export default CourseDetailLayout;
