import { useEffect, useRef, useState } from "react";

interface StepImageUploadProps {
  onNext: () => void;
}

const StepImageUpload = ({ onNext }: StepImageUploadProps) => {
  const [imageUrl, setImageUrl] = useState("");
  const [previewUrl, setPreviewUrl] = useState("");
  const [isCompleted, setIsCompleted] = useState(false);

  const debounceRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      const url = URL.createObjectURL(file);
      setPreviewUrl(url);
      setImageUrl(`uploaded_${file.name}`);
    }
  };

  const handleUrlInput = (url: string) => {
    setImageUrl(url);
    setPreviewUrl(url);
  };

  const removeImage = () => {
    setImageUrl("");
    setPreviewUrl("");
    setIsCompleted(false);
  };

  // ✅ imageUrl이 유효할 경우 자동 제출
  useEffect(() => {
    if (!imageUrl) return;

    if (debounceRef.current) clearTimeout(debounceRef.current);
    debounceRef.current = setTimeout(() => {
      onNext();
      setIsCompleted(true);
    }, 300);
  }, [imageUrl, onNext]);

  return (
    <div className="flex w-full flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌 대표 이미지를 업로드해주세요</p>

      <div className="flex flex-col gap-6">
        {!isCompleted && (
          <>
            {/* 파일 업로드 */}
            <div className="flex flex-col gap-4">
              <label className="font-medium">파일 업로드</label>
              <input
                type="file"
                accept="image/*"
                onChange={handleFileChange}
                className="rounded-lg border px-4 py-2"
              />
            </div>

            {/* URL 입력 */}
            <div className="flex flex-col gap-4">
              <label className="font-medium">또는 이미지 URL 입력</label>
              <div className="flex gap-2">
                <input
                  type="url"
                  value={imageUrl}
                  onChange={(e) => handleUrlInput(e.target.value)}
                  placeholder="https://example.com/image.jpg"
                  className="flex-1 rounded-lg border px-4 py-2"
                />
              </div>
            </div>
          </>
        )}

        {/* 미리보기 */}
        {previewUrl && (
          <div className="flex flex-col gap-2">
            <label className="font-medium">미리보기</label>
            <div className="relative h-48 w-64 overflow-hidden rounded-lg border">
              <img
                src={previewUrl}
                alt="미리보기"
                className="h-full w-full object-cover"
              />
              <button
                onClick={removeImage}
                className="absolute top-2 right-2 flex h-6 w-6 items-center justify-center rounded-full bg-red-500 text-sm text-white hover:bg-red-600"
              >
                ✕
              </button>
            </div>
          </div>
        )}

        {/* ✅ 최종 완료 메시지 */}
        {isCompleted && (
          <p className="mt-6 text-base font-medium text-blue-600">
            강좌 생성에 필요한 모든 과정을 완료했습니다. <br />
            <strong>사이드바에서 "강좌 생성하기" 버튼</strong>을 눌러주세요.
          </p>
        )}
      </div>
    </div>
  );
};

export default StepImageUpload;
