import SearchBar from "@/domain/student/component/search/SearchBar";
import FilterBar from "@/domain/student/component/search/FilterBar";
import { useCallback, useEffect, useState } from "react";
import { useSearchParams } from "react-router";

const SearchResultPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();

  const [searchQuery, setSearchQuery] = useState<string>("");
  const [selectedCategory, setSelectedCategory] = useState<
    string | undefined
  >();
  const [selectedSubcategories, setSelectedSubcategories] = useState<string[]>(
    []
  );

  useEffect(() => {
    const query = searchParams.get("q") || "";
    const category = searchParams.get("category") || undefined;
    const subcategories =
      searchParams.get("subcategories")?.split(",").filter(Boolean) || [];

    setSearchQuery(query);
    setSelectedCategory(category);
    setSelectedSubcategories(subcategories);
  }, []);

  const updateSearchParams = useCallback(
    (query: string, category?: string, subcategories: string[] = []) => {
      const params = new URLSearchParams(searchParams);
      if (params.toString() !== searchParams.toString()) {
        setSearchParams(params);
      }

      if (query.trim()) {
        params.set("q", query);
      }

      if (category) {
        params.set("category", category);
      }

      if (subcategories.length > 0) {
        params.set("subcategories", subcategories.join(","));
      }

      setSearchParams(params);
    },
    [searchParams, setSearchParams]
  );

  const handleSearchInputChange = (q: string) => {
    setSearchQuery(q);
    updateSearchParams(q, selectedCategory, selectedSubcategories);
  };

  const handleCategoryChange = (category: string | undefined) => {
    setSelectedCategory(category);
    updateSearchParams(searchQuery ?? "", category, selectedSubcategories);
  };

  const handleSubcategoryChange = (subcategories: string[]) => {
    setSelectedSubcategories(subcategories);
    updateSearchParams(searchQuery ?? "", selectedCategory, subcategories);
  };

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
