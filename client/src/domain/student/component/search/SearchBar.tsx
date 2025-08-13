interface SearchBarProps {
  value: string;
  onChange: (q: string) => void;
}

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
