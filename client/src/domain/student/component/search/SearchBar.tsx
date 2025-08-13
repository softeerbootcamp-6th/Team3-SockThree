import { useEffect, useState } from "react";

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
  return (
    <input
      placeholder="검색어를 입력하세요"
      value={value}
      onChange={(e) => onChange(e.target.value)}
    />
  );
};

export default SearchBar;
