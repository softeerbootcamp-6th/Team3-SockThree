import { useState, useRef, useEffect } from "react";
import SearchIcon from "@/assets/icons/default/search.svg?react";

const ExpandSearchBar = () => {
  const [isExpanded, setIsExpanded] = useState(false);
  const searchBarRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        searchBarRef.current &&
        !searchBarRef.current.contains(event.target as Node)
      ) {
        setIsExpanded(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <div
      ref={searchBarRef}
      className="relative flex w-[48.4375rem] items-end justify-end"
    >
      {/* 검색 아이콘 버튼 */}
      {!isExpanded && (
        <div className="flex flex-row items-center gap-[1.75rem]">
          <span className="typo-body-2 text-gray-500">
            찾으시는 강의가 없나요?
          </span>
          <button
            onClick={() => setIsExpanded(true)}
            className="flex h-[4.125rem] w-[4.25rem] cursor-pointer items-center justify-center rounded-full bg-gradient-main text-white shadow-md transition-opacity duration-300"
          >
            <SearchIcon className="h-[2rem] w-[2rem] stroke-3" />
          </button>
        </div>
      )}

      {/* 검색 input */}
      <div
        className={`border-circle-gradient-main absolute right-0 flex h-[4.3125rem] items-center gap-[.8125rem] overflow-hidden rounded-full px-[1.5rem] py-[1.25rem] transition-all duration-300 focus:outline-none ${isExpanded ? "w-[48.4375rem] opacity-100" : "pointer-events-none w-0 opacity-0"} `}
      >
        <button className="cursor-pointer">
          <SearchIcon className="h-[2rem] w-[2rem] stroke-3 text-gray-500" />
        </button>
        <input
          type="text"
          placeholder="관심분야를 입력해보세요"
          className="typo-body-5 flex-1 bg-transparent text-black caret-main-500 placeholder:text-gray-500 focus:outline-none"
        />
      </div>
    </div>
  );
};

export default ExpandSearchBar;
