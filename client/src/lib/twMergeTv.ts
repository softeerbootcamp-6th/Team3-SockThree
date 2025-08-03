// utils/twMergeTv.ts
import { tv as baseTv } from "tailwind-variants";
import { twMerge } from "tailwind-merge";

export const tv = (...args: Parameters<typeof baseTv>) => {
  const original = baseTv(...args);

  // 반환되는 slot/class 함수도 twMerge로 감싸기
  const fn = (...innerArgs: Parameters<typeof original>) =>
    twMerge(original(...innerArgs));

  // slot 방식도 지원할 수 있도록 모든 키를 복사
  Object.assign(fn, original);

  return fn;
};
