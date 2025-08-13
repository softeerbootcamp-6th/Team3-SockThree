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
  const [local, setLocal] = useState(value);
  const debounced = useDebouncedValue(local, 300);

  useEffect(() => {
    setLocal(value);
  }, [value]);

  useEffect(() => {
    onChange(debounced);
  }, [debounced, value, onChange]);

  return (
    <input
      placeholder="검색어를 입력하세요"
      value={local}
      onChange={(e) => setLocal(e.target.value)}
    />
  );
};

export default SearchBar;
