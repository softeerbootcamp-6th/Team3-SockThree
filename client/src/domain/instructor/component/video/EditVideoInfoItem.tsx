import CircleDeleteIcon from "@/assets/icons/default/circle-delete.svg?react";
import { useState } from "react";

import { truncateToMaxLength } from "@/shared/utils/textUtils";

interface EditVideoInfoItemProps {
  id: string;
  videoName: string;
  deleteClick: (videoName: string) => void;
}
const EditVideoInfoItem = ({
  id,
  videoName,
  deleteClick,
}: EditVideoInfoItemProps) => {
  const [videoTitle, setVideoTitle] = useState("");
  const [videoDescription, setVideoDescription] = useState("");

  const videoTitleMaxLength = 30;
  const videoDescriptionMaxLength = 100;

  const handleVideoTitleChange = ({
    target,
  }: React.ChangeEvent<HTMLInputElement>) => {
    target.value = truncateToMaxLength(target.value, videoTitleMaxLength);
    setVideoTitle(target.value);
  };

  const handleVideoDescriptionChange = ({
    target,
  }: React.ChangeEvent<HTMLTextAreaElement>) => {
    target.value = truncateToMaxLength(target.value, videoDescriptionMaxLength);
    setVideoDescription(target.value);
  };

  return (
    <div className="flex flex-col gap-2 rounded-[.625rem] border border-gray-400 p-3">
      <div className="flex w-fit max-w-[18.75rem] items-center gap-[.25rem] rounded-[3.125rem] border-[.125rem] border-gray-400 px-[.75rem] py-[.5rem]">
        <button className="cursor-pointer" onClick={() => deleteClick(id)}>
          <CircleDeleteIcon className="w-[1.5rem]" />
        </button>
        <span className="typo-label-0 truncate">{videoName}</span>
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
          {videoTitle.length}/{videoTitleMaxLength}자
        </span>
      </div>

      <textarea
        placeholder="강좌에 대한 설명을 입력해주세요"
        rows={5}
        value={videoDescription}
        maxLength={videoDescriptionMaxLength}
        onChange={handleVideoDescriptionChange}
        className="text-body-5 w-full resize-none rounded-[.625rem] border border-gray-400 px-[1.3125rem] py-[1.375rem] placeholder-gray-400 focus:ring-2 focus:ring-main-500 focus:outline-none"
      />

      <span className="text-body-5 flex justify-end text-gray-400">
        {videoDescription.length}/{videoDescriptionMaxLength}자
      </span>
    </div>
  );
};

export default EditVideoInfoItem;
