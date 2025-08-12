import MyPageCard from "@/domain/student/component/mypage/MyPageCard";

import { useNavigate } from "react-router";

import RightArrowIcon from "@/assets/icons/default/arrow-right.svg?react";

import { fakerKO as faker } from "@faker-js/faker";

const MyPage = () => {
  const userName = "쓰리";
  const currentCourseLength = 5;
  const endCourseLength = 3;
  const heartCourseLength = 2;

  const mockThumbnail = faker.image.urlPicsumPhotos({
    width: 600,
    height: 400,
    grayscale: false,
    blur: 0,
  });

  const navigate = useNavigate();

  const handleCurrentCourseClick = () => {
    navigate("/student/mypage/current-course");
  };

  const handleEndCourseClick = () => {
    navigate("/student/mypage/end-course");
  };

  const handleHeartCourseClick = () => {
    navigate("/student/mypage/heart");
  };

  const handleMyInfoClick = () => {
    navigate("/student/mypage/my-info");
  };

  const cardsData = [
    {
      title: "수강 중인 강좌",
      subTitle: `수강 중인 강좌 ${currentCourseLength}개`,
      thumbnail: mockThumbnail,
      onClick: handleCurrentCourseClick,
    },
    {
      title: "수강 완료 강좌",
      subTitle: `수강 완료 강좌 ${endCourseLength}개`,
      thumbnail: mockThumbnail,
      onClick: handleEndCourseClick,
    },
    {
      courseLength: 5,
      title: "찜 목록이에요",
      subTitle: `찜한 강좌 ${heartCourseLength}개`,
      thumbnail: mockThumbnail,
      onClick: handleHeartCourseClick,
    },
  ];

  return (
    <div className="flex w-full flex-col items-center gap-[2.25rem] pt-[4.9375rem] pr-[3.125rem] pb-[9.625rem] pl-[1.875rem]">
      <p className="typo-title-0 flex h-[7.125rem] w-full text-left whitespace-pre-line">
        {userName}님의 공간이에요 <br /> 어떤 정보를 확인해볼까요?
      </p>
      <hr className="flex h-[.0625rem] w-full text-gray-400" />

      <div className="flex h-[42.6875rem] w-full flex-col gap-[43px]">
        <div className="flex h-[31.25rem] w-full flex-row items-center justify-center gap-[.625rem]">
          {cardsData.map((card, index) => (
            <MyPageCard
              key={index}
              subTitle={card.subTitle}
              title={card.title}
              courseThumbnail={card.thumbnail}
              onClick={card.onClick}
            />
          ))}
        </div>
        <div className="flex h-[3.0625rem] w-full justify-end">
          <button
            className="flex h-[3.0625rem] w-[14.8125rem] cursor-pointer flex-row items-center justify-between p-[.625rem]"
            onClick={handleMyInfoClick}
          >
            <span className="typo-body-1">내 정보 수정하기</span>
            <div className="flex h-[1.75rem] w-[1.75rem] items-center justify-center rounded-full bg-black">
              <RightArrowIcon className="w-[.4456rem] text-white" />
            </div>
          </button>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
