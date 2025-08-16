import { useState, useRef, useCallback, useEffect } from "react";
import { truncateToMaxLength } from "@/shared/utils/textUtils";

interface VideoData {
  id: string;
  videoName: string;
  videoLength: number;
  videoTitle: string;
  videoDescription: string;
}

export const useUploadVideoModal = (initialTitle: string = "") => {
  const [contentsTitle, setContentsTitle] = useState(initialTitle);
  const [uploadVideos, setUploadVideos] = useState<VideoData[]>([]);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const contentsTitleMaxLength = 30;

  useEffect(() => {
    setContentsTitle(initialTitle);
  }, [initialTitle]);

  // 영상 길이 가져오는 helper 함수
  const getVideoDuration = useCallback((file: File): Promise<number> => {
    return new Promise((resolve, reject) => {
      const url = URL.createObjectURL(file);
      const video = document.createElement("video");
      video.preload = "metadata";

      const cleanup = () => {
        URL.revokeObjectURL(url);
        video.src = "";
      };

      video.onloadedmetadata = () => {
        const seconds = video.duration;
        cleanup();
        resolve(seconds);
      };

      video.onerror = () => {
        cleanup();
        reject(new Error("영상 메타데이터를 불러올 수 없어요."));
      };

      video.src = url;
    });
  }, []);

  // 파일 입력 클릭
  const handleFileInputClick = useCallback(() => {
    fileInputRef.current?.click();
  }, []);

  // 파일 선택 처리
  const handleFileSelect = useCallback(
    async (event: React.ChangeEvent<HTMLInputElement>) => {
      const file = event.target.files?.[0];
      if (!file) return;

      try {
        const durationSec = await getVideoDuration(file);
        const newItem: VideoData = {
          id: crypto.randomUUID(),
          videoName: file.name,
          videoLength: Math.round(durationSec) / 60,
          videoTitle: "",
          videoDescription: "",
        };

        setUploadVideos((prev) => [...prev, newItem]);
      } catch (error) {
        console.error("파일 처리 중 오류:", error);
      } finally {
        event.target.value = "";
      }
    },
    [getVideoDuration]
  );

  // 비디오 삭제
  const handleVideoDelete = useCallback((id: string) => {
    setUploadVideos((prev) => prev.filter((video) => video.id !== id));
  }, []);

  // 목차명 변경
  const handleContentsTitleChange = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      const { value } = event.target;
      const truncatedValue = truncateToMaxLength(value, contentsTitleMaxLength);
      event.target.value = truncatedValue;
      setContentsTitle(truncatedValue);
    },
    [contentsTitleMaxLength]
  );

  // 상태 초기화
  const resetState = useCallback(() => {
    setUploadVideos([]);
    setContentsTitle("");
  }, []);

  // 제목 업데이트
  const updateTitle = useCallback((newTitle: string) => {
    setContentsTitle(newTitle);
  }, []);

  // 업로드 실행
  const handleUpload = (chapterId: number | null) => {
    if (uploadVideos.length === 0) {
      alert("업로드할 동영상을 선택해주세요.");
      return false;
    }

    if (!contentsTitle.trim()) {
      alert("목차명을 입력해주세요.");
      return false;
    }

    // TODO: 실제 업로드 API 호출
    console.log("업로드 데이터:", {
      chapterId,
      contentsTitle,
      videos: uploadVideos,
    });

    resetState();
    return true;
  };

  return {
    contentsTitle,
    uploadVideos,
    fileInputRef,
    contentsTitleMaxLength,

    handleFileInputClick,
    handleFileSelect,
    handleVideoDelete,
    handleContentsTitleChange,
    resetState,
    updateTitle,
    handleUpload,
  };
};

export type { VideoData };
