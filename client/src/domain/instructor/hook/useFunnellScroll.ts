import { useEffect, useRef } from "react";

export const useFunnelScroll = ({
  activeId,
  scrollMode = "start",
}: {
  activeId: string;
  scrollMode?: ScrollLogicalPosition;
}) => {
  const containerRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    const target = document.getElementById(activeId);
    if (target) {
      requestAnimationFrame(() => {
        target.scrollIntoView({
          behavior: "smooth",
          block: scrollMode,
        });
      });
    }
  }, [activeId, scrollMode]);

  return {
    containerRef,
  };
};
