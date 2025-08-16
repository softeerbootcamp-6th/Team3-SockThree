import FilterIcon from "@/assets/icons/default/filtering.svg?react";

interface FilterButtonProps {
  onClick?: () => void;
  isActive?: boolean;
}

const FilterButton = ({ onClick, isActive = false }: FilterButtonProps) => {
  return (
    <button
      onClick={onClick}
      className={`flex items-center gap-2 rounded-md px-2 py-2 text-sm transition-all duration-100 ${
        isActive
          ? "border-1 border-main-500 bg-main-50 font-bold text-main-600"
          : "border border-gray-300 bg-white font-normal text-gray-600 hover:bg-gray-50"
      } `}
    >
      <FilterIcon
        className={`h-[1.4rem] w-[1.4rem] ${isActive ? "text-main-500" : "text-gray-500"}`}
      />
      필터
    </button>
  );
};

export default FilterButton;
