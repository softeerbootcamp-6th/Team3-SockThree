import UploadVideoModal from "@/domain/instructor/component/video/UploadVideoModal";
import VideoList from "@/domain/instructor/component/video/VideoList";
import Button from "@/shared/components/Button";
import useModal from "@/shared/hook/useModal";

import { useState } from "react";

const VideoUploadPage = () => {
  const { modalRef, openModal, closeModal } = useModal();
  const [selectedChapterId, setSelectedChapterId] = useState<number | null>(
    null
  );

  const handleCreateClick = () => {
    setSelectedChapterId(null);
    openModal();
  };

  const handleEditClick = (videoListId: number) => {
    setSelectedChapterId(videoListId);
    openModal();
  };

  const handleCloseClick = () => {
    setSelectedChapterId(null);
    closeModal();
  };

  const getSelectedTitle = (selectedChapterId: number | null) => {
    if (selectedChapterId === null) {
      return "";
    }

    const selectedVideoList = videoListData.find(
      (item) => item.chapterId === selectedChapterId
    );
    return selectedVideoList?.chapterTitle || "";
  };

  const videoListData = [
    {
      chapterId: 1,
      chapterTitle: "강의 목차 1",
      videos: [
        {
          id: 1,
          videoTitle: "비디오 1",
          duration: 120,
          uploadDate: new Date(),
          releaseDate: new Date(),
          isUploading: true,
          uploadProgress: 75,
        },
        {
          id: 2,
          videoTitle: "비디오 2",
          duration: 150,
          uploadDate: new Date(),
          releaseDate: new Date(),
          isUploading: false,
          uploadProgress: 100,
        },
      ],
    },
    {
      chapterId: 2,
      chapterTitle: "강의 목차 2",
      videos: [
        {
          id: 3,
          videoTitle: "비디오 3",
          duration: 180,
          uploadDate: new Date(),
          releaseDate: new Date(),
          isUploading: true,
          uploadProgress: 50,
        },
        {
          id: 4,
          videoTitle: "비디오 4",
          duration: 200,
          uploadDate: new Date(),
          releaseDate: new Date(),
          isUploading: false,
          uploadProgress: 100,
        },
      ],
    },
  ];

  return (
    <>
      {/* 모달 */}
      <UploadVideoModal
        ref={modalRef}
        onClose={handleCloseClick}
        title={getSelectedTitle(selectedChapterId)}
        chapterId={selectedChapterId}
      />
      <div className="flex flex-col items-end gap-5">
        <Button variant="outline" size="sm" onClick={handleCreateClick}>
          강의 목차 생성하기
        </Button>
        {videoListData.map((videoList) => (
          <VideoList
            key={videoList.chapterId}
            chapterId={videoList.chapterId}
            chapterTitle={videoList.chapterTitle}
            videos={videoList.videos}
            onEditClick={() => handleEditClick(videoList.chapterId)}
          />
        ))}
      </div>
    </>
  );
};

export default VideoUploadPage;
