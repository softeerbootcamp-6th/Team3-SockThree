import FilterIcon from "@/assets/icons/default/filtering.svg?react";

interface FilterButtonProps {
  onClick?: () => void;
}

const FilterButton = ({ onClick }: FilterButtonProps) => {
  return (
    <button
      className="flex cursor-pointer items-center justify-center gap-[0.4rem] rounded-full bg-white px-[0.5rem] py-[0.3rem] shadow-bg"
      onClick={onClick}
    >
      <div className="text- h-[1.5rem] w-[1.5rem] rounded-full bg-gray-400 p-[0.25rem]">
        <FilterIcon className="" />
      </div>
      <span className="typo-label-4">필터</span>
    </button>
  );
};

export default FilterButton;
