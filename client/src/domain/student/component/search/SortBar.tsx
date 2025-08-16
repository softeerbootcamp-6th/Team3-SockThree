import Select from "@/shared/components/Select";

interface SortBarProps {
  value?: string;
  onSortChange: (value: string) => void;
}

const SortBar = ({ value, onSortChange }: SortBarProps) => {
  return (
    <>
      <Select
        placeholder="정렬 방식"
        className="typo-body-0 w-[7.6rem] border-none bg-bg shadow-none"
        value={value}
        options={[
          { value: "recent", label: "최신순" },
          { value: "popular", label: "인기순" },
          { value: "rating", label: "평점순" },
          { value: "priceAsc", label: "가격 낮은 순" },
          { value: "priceDesc", label: "가격 높은 순" },
        ]}
        onValueChange={onSortChange}
      />
    </>
  );
};

export default SortBar;
