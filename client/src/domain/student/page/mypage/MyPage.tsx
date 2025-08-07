import CurrentCourseCard from "@/domain/student/component/mypage/CurrentCourseCard";
import SmallCourseCard from "@/domain/student/component/mypage/SmallCourseCard";
import { fakerKO as faker, he } from "@faker-js/faker";
const MyPage = () => {
  const userName = "쓰리";
  const currentCourseLength = 5;
  const currentCourseThumbnail = faker.image.urlPicsumPhotos({
    width: 600,
    height: 400,
    grayscale: false,
    blur: 0,
  });

  const endCourseLength = 3;
  const endCourseThumbnail = faker.image.urlPicsumPhotos({
    width: 600,
    height: 400,
    grayscale: false,
    blur: 0,
  });
  const endCourseSubTitle = `수강 완료 강좌 ${endCourseLength}개`;
  const endCourseTitle = "수강 완료 강좌";

  const heartCourseLength = 2;
  const heartCourseThumbnail = faker.image.urlPicsumPhotos({
    width: 600,
    height: 400,
    grayscale: false,
    blur: 0,
  });
  const heartCourseSubTitle = `찜한 강좌 ${heartCourseLength}개`;
  const heartCourseTitle = "찜 목록이에요";

  return (
    <div className="flex h-full w-full flex-col items-center gap-[2.25rem] pt-[4.9375rem] pr-[3.125rem] pb-[1.375rem] pl-[1.875rem]">
      <p className="typo-title-0 flex h-[7.125rem] w-full text-left whitespace-pre-line">
        {userName}님의 공간이에요 <br /> 어떤 정보를 확인해볼까요?
      </p>
      <div className="flex h-[.0625rem] w-full bg-gray-400" />

      <div className="flex h-[51.8125rem] w-full flex-col">
        <div className="flex h-[36.9375rem] w-full flex-row">
          <CurrentCourseCard
            currentCourseLength={currentCourseLength}
            currentCourseThumbnail={currentCourseThumbnail}
          />
          <SmallCourseCard
            subTitle={endCourseSubTitle}
            title={endCourseTitle}
            courseThumbnail={endCourseThumbnail}
          />
          <SmallCourseCard
            subTitle={heartCourseSubTitle}
            title={heartCourseTitle}
            courseThumbnail={heartCourseThumbnail}
          />
        </div>
        <div className="flex h-[3.0625rem] w-full flex-row"></div>
      </div>
    </div>
  );
};

export default MyPage;
