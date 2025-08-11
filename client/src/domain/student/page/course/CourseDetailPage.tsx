// src/pages/CourseDetailPage.tsx

// import CourseHero from "@/domain/course/components/CourseHero";
// import CourseTabs from "@/domain/course/components/CourseTabs";
// import InstructorCard from "@/domain/course/components/InstructorCard";
// import CourseStatList from "@/domain/course/components/CourseStatList";
// import RichText from "@/shared/components/RichText";
// import ApplySidebar from "@/domain/course/components/ApplySidebar";

import { CourseInfoCardDefault } from "@/domain/student/component/course/CourseInfoCardDefault";

const CourseDetailPage = () => {
  return (
    <main className="mx-auto w-full max-w-screen-2xl px-4 sm:px-6 lg:px-8">
      {/* 메인/사이드 2열 그리드 */}
      <div className="grid grid-cols-1 gap-6 py-6 lg:grid-cols-12 lg:gap-8">
        {/* 메인 영역 */}
        <section className="h-screen min-h-[50vh] bg-gray-800 lg:col-span-8 xl:col-span-9">
          메인
        </section>

        {/* 사이드바 영역 */}
        <aside className="lg:col-span-4 xl:col-span-3">
          {/* 사이드바 카드 자체를 sticky 처리.
                top 오프셋은 헤더 높이와 동일하게 맞춤(h-16 => top-16) */}
          <div className="sticky top-[6rem]">
            <CourseInfoCardDefault status="recruiting" />
          </div>
        </aside>
      </div>
    </main>
  );
};

export default CourseDetailPage;
