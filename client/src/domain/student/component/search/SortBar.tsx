import Select from "@/shared/components/Select";

const SortBar = () => {
  return (
    <>
      <Select
        placeholder="정렬 방식"
        className="w-[7.6rem] border-none bg-bg shadow-none"
        options={[
          { value: "recent", label: "최신순" },
          { value: "popular", label: "인기순" },
          { value: "rating", label: "평점순" },
          { value: "priceAsc", label: "가격 낮은 순" },
          { value: "priceDesc", label: "가격 높은 순" },
        ]}
        onValueChange={(value) => {
          console.log("Selected sort option:", value);
        }}
      />
    </>
  );
};

export default SortBar;
