import { useEffect, useState } from "react";
import SearchIcon from "@/assets/icons/default/search.svg?react";

interface SearchBarProps {
  value: string;
  onChange: (q: string) => void;
}

export const useDebouncedValue = (value: string, delayMs: number) => {
  const [debounced, setDebounced] = useState(value);
  useEffect(() => {
    const id = setTimeout(() => setDebounced(value), delayMs);
    return () => clearTimeout(id);
  }, [value, delayMs]);
  return debounced;
};

const SearchBar = ({ value, onChange }: SearchBarProps) => {
  const [local, setLocal] = useState(value);
  const debounced = useDebouncedValue(local, 300);

  useEffect(() => {
    setLocal(value);
  }, [value]);

  useEffect(() => {
    onChange(debounced);
  }, [debounced, value]);

  return (
    <div className="flex w-full max-w-[40rem] items-center rounded-full border border-gray-300 bg-white px-[1rem] py-[1rem]">
      <SearchIcon className="h-[1.3rem] w-[1.3rem] text-gray-600" />
      <input
        className="ml-2 w-full border-none text-sm focus:outline-none"
        placeholder="검색어를 입력하세요"
        type="text"
        value={local}
        onChange={(e) => setLocal(e.target.value)}
      />
    </div>
  );
};

export default SearchBar;
