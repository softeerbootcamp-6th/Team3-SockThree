// VideoEditModal.tsx
import UploadVideoItem from "@/domain/instructor/component/video/UploadVideoItem";
import Button from "@/shared/components/Button";
import GradationChip from "@/shared/components/GradationChip";
import Modal from "@/shared/components/Modal";
import { forwardRef, useState } from "react";

type VideoEditModalProps = {
  onClose: () => void;
};

const VideoEditModal = forwardRef<HTMLDialogElement, VideoEditModalProps>(
  ({ onClose }, ref) => {
    const [contentsTitle, setContentsTitle] = useState("");
    const contentsTitleMaxLength = 30;

    const handleContentsTitleChange = (
      event: React.ChangeEvent<HTMLInputElement>
    ) => {
      setContentsTitle(event.target.value);
    };
    return (
      <Modal ref={ref}>
        <div className="flex h-[40.5rem] w-[39.625rem] flex-col px-[1.6875rem] py-[2rem]">
          {/* 강좌명 */}
          <div className="flex w-full flex-col gap-[1.375rem]">
            <label className="typo-body-4">목차명</label>
            <div className="relative w-full">
              <input
                type="text"
                placeholder="강좌명을 입력해주세요"
                value={contentsTitle}
                onChange={handleContentsTitleChange}
                maxLength={contentsTitleMaxLength}
                className="text-body-5 w-full rounded-[.625rem] border border-gray-400 px-[1.3125rem] py-[1.375rem] placeholder-gray-400 focus:ring-2 focus:ring-main-500 focus:outline-none"
              />
              <span className="text-body-5 absolute top-1/2 right-[1.4375rem] -translate-y-1/2 text-gray-400">
                {contentsTitle.length}/{contentsTitleMaxLength}자
              </span>
            </div>
          </div>

          <hr className="mt-[1.4375rem] mb-[1.4375rem] h-[.0625rem] w-full text-gray-400" />

          {/* 강의 업로드 */}
          <div className="flex w-full flex-col gap-[.8125rem]">
            <div className="flex w-full flex-row items-center justify-between">
              <label className="typo-body-4">강의 동영상 업로드</label>
              <button className="typo-label-0 cursor-pointer">
                <GradationChip>+ 동영상 업로드</GradationChip>
              </button>
            </div>
            <div className="flex h-[17.4375rem] flex-col gap-[1.125rem] overflow-y-auto">
              <UploadVideoItem videoName="예시 비디오.mp4" />
            </div>
          </div>

          {/* 버튼 영역 */}
          <div className="mt-[1.6875rem] flex w-full justify-end gap-[1rem]">
            <Button
              variant={"default"}
              className="typo-body-4 flex-1/2 rounded-[.9375rem] bg-gray-200 px-4 py-2 text-black hover:bg-gray-400"
              onClick={onClose}
            >
              닫기
            </Button>

            <Button
              variant={"outline"}
              className="typo-body-4 flex-1/2 rounded-[.9375rem]"
              onClick={() => {}}
            >
              강의 등록 완료
            </Button>
          </div>
        </div>
      </Modal>
    );
  }
);

VideoEditModal.displayName = "VideoEditModal";

export default VideoEditModal;
