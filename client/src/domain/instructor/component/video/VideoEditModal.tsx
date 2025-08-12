import UploadVideoItem from "@/domain/instructor/component/video/UploadVideoItem";
import Button from "@/shared/components/Button";
import GradationChip from "@/shared/components/GradationChip";
import Modal from "@/shared/components/Modal";
import { forwardRef, useState } from "react";

interface VideoEditModalProps {
  onClose: () => void;
  title: string;
  id?: number;
}
interface VideoData {
  id: string;
  videoName: string;
  videoLength: number;
  videoTitle: string;
  videoDescription: string;
}

const VideoEditModal = forwardRef<HTMLDialogElement, VideoEditModalProps>(
  ({ onClose, title }, ref) => {
    const [contentsTitle, setContentsTitle] = useState(title);
    const [uploadVideos, setUploadVideos] = useState<VideoData[]>([]);
    const contentsTitleMaxLength = 30;

    const handleVideoAddButtonClick = () => {
      const input = document.createElement("input");
      input.type = "file";
      input.accept = "video/mp4";

      input.onchange = (event) => {
        const file = (event.target as HTMLInputElement).files?.[0];
        if (!file) return;

        const newItem: VideoData = {
          id: crypto.randomUUID(),
          videoName: file.name,
          videoLength: file.size,
          videoTitle: "",
          videoDescription: "",
        };

        setUploadVideos((prev) => [...prev, newItem]);
      };

      document.body.appendChild(input);
      input.click();
      document.body.removeChild(input);
    };

    const handleVideoDelete = (id: string) => {
      setUploadVideos((prev) => prev.filter((v) => v.id !== id));
    };

    const handleContentsTitleChange = (
      event: React.ChangeEvent<HTMLInputElement>
    ) => {
      setContentsTitle(event.target.value);
    };

    const handleCloseClick = () => {
      setUploadVideos([]);
      onClose();
    };

    const handleVideoUploadClick = () => {
      // 동영상 업로드 로직 추가 예정
      setUploadVideos([]);
      onClose();
    };

    const renderVideoUploadItems = (uploadVideos: VideoData[]) => {
      if (uploadVideos.length === 0) {
        return (
          <div className="flex h-full items-center justify-center text-gray-400">
            동영상 업로드 버튼을 눌러 동영상을 업로드 해주세요.
          </div>
        );
      } else {
        return uploadVideos.map((video) => (
          <UploadVideoItem
            key={video.id}
            id={video.id}
            videoName={video.videoName}
            deleteClick={handleVideoDelete}
          />
        ));
      }
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
                onClick={handleVideoAddButtonClick}
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
              onClick={handleCloseClick}
            >
              닫기
            </Button>

            <Button
              variant="outline"
              className="typo-body-4 flex-1/2 rounded-[.9375rem]"
              onClick={handleVideoUploadClick}
            >
              강의 등록 완료
            </Button>
          </div>
        </div>
      </Modal>
    );
  }
);

export default VideoEditModal;
