import CircleDeleteIcon from "@/assets/icons/default/circle-delete.svg?react";
import { useState } from "react";

interface UploadVideoItemProps {
  videoName: string;
}
const UploadVideoItem = ({ videoName }: UploadVideoItemProps) => {
  const [videoTitle, setVideoTitle] = useState("");

  const videoTitleMaxLength = 30;

  const handleVideoTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setVideoTitle(e.target.value);
  };
  return (
    <div className="flex flex-col gap-2 rounded-[.625rem] border border-gray-400 p-3">
      <div className="flex max-w-[25rem] items-center gap-[.25rem] rounded-[3.125rem] border-[.125rem] border-gray-400 px-[.75rem] py-[.5rem]">
        <button className="cursor-pointer">
          <CircleDeleteIcon className="w-[1.5rem]" />
        </button>
        <span className="typo-label-0">{videoName}</span>
      </div>
      <div className="relative w-full">
        <input
          type="text"
          placeholder="강좌명을 입력해주세요"
          value={videoTitle}
          onChange={handleVideoTitleChange}
          maxLength={videoTitleMaxLength}
          className="text-body-5 w-full rounded-[.625rem] border border-gray-400 px-[1.3125rem] py-[1.375rem] placeholder-gray-400 focus:ring-2 focus:ring-main-500 focus:outline-none"
        />
        <span className="text-body-5 absolute top-1/2 right-[1.4375rem] -translate-y-1/2 text-gray-400">
          {videoTitle.length}/{videoTitleMaxLength}
        </span>
      </div>
    </div>
  );
};

export default UploadVideoItem;
