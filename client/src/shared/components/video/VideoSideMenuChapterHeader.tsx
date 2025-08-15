import TriangleUp from "@/assets/icons/default/triangle-up.svg?react";
import TriangleDown from "@/assets/icons/default/triangle-down.svg?react";

interface VideoSideMenuChapterHeaderProps {
  id: number;
  title?: string;
  isOpen: boolean;
  onToggle: (id: number) => void;
  isMenuOpen: boolean;
}

const VideoSideMenuChapterHeader = ({
  id,
  title,
  isOpen,
  onToggle,
  isMenuOpen,
}: VideoSideMenuChapterHeaderProps) => {
  if (!isMenuOpen) return null;

  return (
    <button
      onClick={() => onToggle(id)}
      className="typo-body-5 flex w-full items-center border-y border-gray-300 bg-white p-5"
    >
      <span>
        {isOpen ? (
          <TriangleUp className="w-[.9375rem] text-gray-500" />
        ) : (
          <TriangleDown className="w-[.9375rem] text-gray-500" />
        )}
      </span>
      <span className="flex-1 truncate">{title}</span>
  
    </button>
  );
};

export default VideoSideMenuChapterHeader;
