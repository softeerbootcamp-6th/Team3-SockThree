// VideoEditModal.tsx
import Modal from "@/shared/components/Modal";
import { forwardRef } from "react";

type VideoEditModalProps = {
  onClose: () => void;
};

const VideoEditModal = forwardRef<HTMLDialogElement, VideoEditModalProps>(
  ({ onClose }, ref) => {
    return (
      <Modal ref={ref}>
        <div className="fixed top-1/2 left-1/2 h-[40.5rem] w-[39.625rem] -translate-x-1/2 -translate-y-1/2 transform overflow-scroll rounded-lg bg-white p-6 shadow-lg">
          {/* 강좌명 */}
          <div className="flex w-full flex-col gap-2">
            <label className="typo-label-1 text-gray-900">강좌명</label>
            <input
              type="text"
              placeholder="강좌명을 입력해주세요"
              maxLength={10}
              className="w-full rounded-lg border border-gray-300 px-4 py-2 text-sm placeholder-gray-400 focus:ring-2 focus:ring-main-500 focus:outline-none"
            />
            <span className="self-end text-xs text-gray-400">0/10자</span>
          </div>

          {/* 강의 업로드 */}
          <div className="mt-6 flex w-full flex-col gap-3">
            <label className="typo-label-1 text-gray-900">강의 업로드</label>
            <button className="rounded-lg bg-main-100 px-4 py-2 text-sm font-medium text-main-700 hover:bg-main-200">
              + 동영상 업로드
            </button>

            {/* 업로드된 동영상 1 */}
            <div className="flex flex-col gap-2 rounded-lg border border-gray-200 p-3">
              <div className="flex items-center justify-between">
                <span className="text-sm text-gray-700">동영상 이름.mp4</span>
                <button className="text-gray-400 hover:text-gray-600">✕</button>
              </div>
              <input
                type="text"
                placeholder="강의 제목을 입력해주세요"
                maxLength={10}
                className="w-full rounded-lg border border-gray-300 px-4 py-1.5 text-sm placeholder-gray-400 focus:ring-2 focus:ring-main-500 focus:outline-none"
              />
              <span className="self-end text-xs text-gray-400">0/10자</span>
            </div>
          </div>

          {/* 버튼 영역 */}
          <div className="mt-6 flex w-full justify-end gap-2">
            <button
              onClick={onClose}
              className="rounded-lg border border-gray-200 bg-gray-50 px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
            >
              닫기
            </button>
            <button className="rounded-lg border border-main-500 bg-main-500 px-4 py-2 text-sm text-white hover:bg-main-600">
              강의 등록 완료
            </button>
          </div>
        </div>
      </Modal>
    );
  }
);

VideoEditModal.displayName = "VideoEditModal";

export default VideoEditModal;
