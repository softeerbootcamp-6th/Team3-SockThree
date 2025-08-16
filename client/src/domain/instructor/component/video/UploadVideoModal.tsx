import EditVideoInfoItem from "@/domain/instructor/component/video/EditVideoInfoItem";
import Button from "@/shared/components/Button";
import GradationChip from "@/shared/components/GradationChip";
import Modal from "@/shared/components/Modal";
import { forwardRef } from "react";
import { useUploadVideoModal } from "@/domain/instructor/hook/useUploadVideoModal";

interface UploadVideoModalProps {
  onClose: () => void;
  title: string;
  chapterId: number | null;
}
interface VideoData {
  id: string;
  videoName: string;
  videoLength: number;
  videoTitle: string;
  videoDescription: string;
}

const UploadVideoModal = forwardRef<HTMLDialogElement, UploadVideoModalProps>(
  ({ onClose, title, chapterId }, ref) => {
    const {
      contentsTitle,
      uploadVideos,
      fileInputRef,
      contentsTitleMaxLength,
      handleFileInputClick,
      handleFileSelect,
      handleVideoDelete,
      handleContentsTitleChange,
      handleUpload,
    } = useUploadVideoModal(title);

    const renderVideoUploadItems = (uploadVideos: VideoData[]) => {
      if (uploadVideos.length === 0) {
        return (
          <div className="flex h-full items-center justify-center text-gray-400">
            동영상 업로드 버튼을 눌러 동영상을 업로드 해주세요.
          </div>
        );
      } else {
        return uploadVideos.map((video) => (
          <EditVideoInfoItem
            key={video.id}
            id={video.id}
            videoName={video.videoName}
            deleteClick={handleVideoDelete}
          />
        ));
      }
    };

    return (
      <>
        <Modal ref={ref}>
          <div className="flex h-[40.5rem] w-[39.625rem] flex-col px-[1.6875rem] py-[2rem]">
            {/* 강좌명 */}
            <div className="flex w-full flex-col gap-[1.375rem]">
              <label className="typo-body-4">목차명</label>
              <div className="relative w-full">
                <input
                  type="text"
                  placeholder="목차명을 입력해주세요"
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
                <button
                  className="typo-label-0 cursor-pointer"
                  onClick={handleFileInputClick}
                >
                  <GradationChip>+ 동영상 업로드</GradationChip>
                </button>
              </div>
              <div className="flex h-[17.4375rem] flex-col gap-[1.125rem] overflow-y-auto">
                {renderVideoUploadItems(uploadVideos)}
              </div>
            </div>

            {/* 버튼 영역 */}
            <div className="mt-[1.6875rem] flex w-full justify-end gap-[1rem]">
              <Button
                variant="default"
                className="typo-body-4 flex-1/2 rounded-[.9375rem] bg-gray-200 px-4 py-2 text-black hover:bg-gray-400"
                onClick={onClose}
              >
                닫기
              </Button>

              <Button
                variant="outline"
                className="typo-body-4 flex-1/2 rounded-[.9375rem]"
                onClick={() => handleUpload(chapterId)}
              >
                강의 등록 완료
              </Button>
            </div>
          </div>
        </Modal>
        <input
          type="file"
          accept="video/mp4"
          hidden
          ref={fileInputRef}
          onChange={handleFileSelect}
        />
      </>
    );
  }
);

export default UploadVideoModal;
