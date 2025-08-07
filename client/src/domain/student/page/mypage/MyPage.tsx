const MyPage = () => {
  const userName = "쓰리";

  return (
    <div className="flex h-full w-full flex-col items-center gap-[2.25rem] pt-[4.9375rem] pr-[3.125rem] pb-[1.375rem] pl-[1.875rem]">
      <p className="typo-title-0 flex h-[7.125rem] w-full text-left whitespace-pre-line">
        {userName}님의 공간이에요 <br /> 어떤 정보를 확인해볼까요?
      </p>
      <div className="flex h-[.0625rem] w-full bg-gray-400" />

      <div className="flex h-[51.8125rem] w-full flex-col">
        <div className="h-[36.9375rem flex w-full flex-row"></div>
        <div className="flex h-[3.0625rem] w-full flex-row"></div>
      </div>
    </div>
  );
};

export default MyPage;
