import { useEffect, useState, useRef, useCallback } from "react";

interface CarouselOptions<T> {
  originalListItems: T[];
  itemHeight: number;
  itemsPerPage: number;
  autoInterval?: number;
  transitionDuration?: number;
}

const useCarousel = <T>({
  originalListItems,
  itemHeight,
  itemsPerPage,
  autoInterval = 3000,
  transitionDuration = 700,
}: CarouselOptions<T>) => {
  const [index, setIndex] = useState(0);
  const [isTransition, setIsTransition] = useState(true);
  const intervalRef = useRef<NodeJS.Timeout | null>(null);

  const createCarouselListItem = (): T[] => {
    const repeatedList = Array.from(
      { length: itemsPerPage },
      () => originalListItems
    ).flat();

    // 첫 번째 리스트로 자연스럽게 넘어가기 위해 요소 추가
    const overflowItems = originalListItems.slice(0, itemsPerPage);

    return [...repeatedList, ...overflowItems];
  };

  const carouselListItems = createCarouselListItem();

  const startAutoSlide = useCallback(() => {
    if (intervalRef.current) return;
    intervalRef.current = setInterval(() => {
      setIndex((prev) => {
        if (prev === originalListItems.length - 1) {
          setIndex(originalListItems.length);

          // 마지막 transition이 끝난 후에 transition을 제거하고 첫 번째로 점프
          setTimeout(() => {
            setIsTransition(false);
            setIndex(0);
          }, transitionDuration);
        } else {
          setIsTransition(true);
        }
        return prev + 1;
      });
    }, autoInterval);
  }, [autoInterval, originalListItems.length, transitionDuration]);

  const stopAutoSlide = useCallback(() => {
    if (intervalRef.current) {
      clearInterval(intervalRef.current);
      intervalRef.current = null;
    }
  }, []);

  useEffect(() => {
    startAutoSlide();
    return () => stopAutoSlide();
  }, [startAutoSlide, stopAutoSlide]);

  const offsetY = index * itemHeight * itemsPerPage;

  return {
    carouselListItems,
    index,
    isTransition,
    offsetY,
    handleMouseEnter: stopAutoSlide,
    handleMouseLeave: startAutoSlide,
  };
};

export { useCarousel };
