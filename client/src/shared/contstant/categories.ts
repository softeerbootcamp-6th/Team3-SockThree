// 대분류
export const categories = [
  "운동",
  "미술",
  "재테크",
  "디지털 정보화",
  "음악",
  "생활",
  "자격증",
] as const;

// 소분류
export const subCategories: Record<string, string[]> = {
  운동: [
    "요가",
    "필라테스",
    "실버에어로빅",
    "탁구",
    "걷기 모임",
    "골프",
    "수영",
    "라인댄스",
  ] as const,
  미술: ["수채화", "유화", "캘리그라피", "색연필화"] as const,
  음악: ["합창", "오카리나", "우쿨렐레", "통기타"] as const,
};
