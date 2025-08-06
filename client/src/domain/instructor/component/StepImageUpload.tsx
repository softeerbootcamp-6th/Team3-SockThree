import { useState } from "react";
import { useFormContext } from "react-hook-form";

interface StepImageUploadProps {
  onNext: () => void;
}

const StepImageUpload = ({ onNext }: StepImageUploadProps) => {
  const [imageUrl, setImageUrl] = useState("");
  const [previewUrl, setPreviewUrl] = useState("");

  const { watch } = useFormContext();

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      // 파일을 URL로 변환하여 미리보기 생성
      const url = URL.createObjectURL(file);
      setPreviewUrl(url);
      // 실제로는 서버에 업로드하고 URL을 받아와야 하지만, 여기서는 임시로 파일 이름 사용
      setImageUrl(`uploaded_${file.name}`);
    }
  };

  const handleUrlInput = (url: string) => {
    setImageUrl(url);
    setPreviewUrl(url);
  };

  const handleSubmit = () => {
    if (imageUrl) {
      onNext();
    }
  };

  const removeImage = () => {
    setImageUrl("");
    setPreviewUrl("");
  };

  return (
    <div className="border- flex w-[71rem] flex-col gap-[50px] rounded-[var(--radius-20)] bg-white px-[40px] py-[36px]">
      <p className="typo-title-5">강좌 대표 이미지를 업로드해주세요</p>
      <div className="flex flex-col gap-6">
        {/* 파일 업로드 섹션 */}
        <div className="flex flex-col gap-4">
          <label className="font-medium">파일 업로드</label>
          <input
            type="file"
            accept="image/*"
            onChange={handleFileChange}
            className="rounded-lg border px-4 py-2"
          />
        </div>

        {/* URL 입력 섹션 */}
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

        {/* 이미지 미리보기 */}
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

        <button
          onClick={handleSubmit}
          disabled={!imageUrl}
          className="mt-4 rounded-lg bg-blue-500 px-6 py-3 text-white disabled:bg-gray-300"
        >
          강좌 생성 완료
        </button>
      </div>
    </div>
  );
};

export default StepImageUpload;
