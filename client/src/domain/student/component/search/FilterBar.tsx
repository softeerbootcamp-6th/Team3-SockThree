interface FilterBarProps {
  selectedCategory?: string;
  selectedSubcategories?: string[];
}

const FilterBar = ({
  selectedCategory,
  selectedSubcategories = [],
}: FilterBarProps) => {
  const hasFilter = selectedCategory || selectedSubcategories.length > 0;

  return (
    <div>
      <button>필터</button>
      {hasFilter && (
        <>
          {selectedCategory && <span>{selectedCategory}</span>}
          {selectedSubcategories.map((sub) => (
            <span>{sub}</span>
          ))}
        </>
      )}
    </div>
  );
};

export default FilterBar;
