import { useEffect, useRef } from "react";

export const useFunnelScroll = ({ stepIndex }: { stepIndex: number }) => {
  const containerRef = useRef<HTMLDivElement | null>(null);
  const stepRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    requestAnimationFrame(() => {
      stepRef.current?.scrollIntoView({
        behavior: "smooth",
        block: "start",
      });
    });
  }, [stepIndex]);

  return {
    containerRef,
    stepRef,
  };
};
