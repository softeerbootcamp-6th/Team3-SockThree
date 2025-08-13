import SearchBar from "@/domain/student/component/search/SearchBar";
import FilterBar from "@/domain/student/component/search/FilterBar";
import { useState } from "react";

const SearchResultPage = () => {
  const [searchQuery, setSearchQuery] = useState<string>();
  const [selectedCategory, setSelectedCategory] = useState<string>();
  const [selectedSubcategories, setSelectedSubcategories] = useState<string[]>(
    []
  );

  const handleSearchInputChange = (q: string) => {
    setSearchQuery(q);
  };

  const handleCategoryChange = (category: string) => {
    setSelectedCategory(category);
  };

  const handleSubcategoryChange = (subcategories: string[]) => {
    setSelectedSubcategories(subcategories);
  };

  console.log(searchQuery, selectedCategory, selectedSubcategories);

  return (
    <div className="mt-[10rem] w-[100rem]">
      <SearchBar value={searchQuery ?? ""} onChange={handleSearchInputChange} />
      <FilterBar
        selectedCategory={selectedCategory}
        selectedSubcategories={selectedSubcategories}
        onCategoryChange={handleCategoryChange}
        onSubcategoryChange={handleSubcategoryChange}
      />
    </div>
  );
};

export default SearchResultPage;
