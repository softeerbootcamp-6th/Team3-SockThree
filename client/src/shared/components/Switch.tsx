import * as React from "react";
import { useState } from "react";
import * as SwitchPrimitive from "@radix-ui/react-switch";
import { cn } from "@/lib/utils";

import ArrowRight from "@/assets/icons/gradient/arrow_right.svg?react";
import ArrowLeft from "@/assets/icons/gradient/arrow_left.svg?react";

const Switch = ({
  className,
  ...props
}: React.ComponentProps<typeof SwitchPrimitive.Root>) => {
  const [isChecked, setIsChecked] = useState(false);

  return (
    <SwitchPrimitive.Root
      checked={isChecked}
      onCheckedChange={setIsChecked}
      data-slot="switch"
      className={cn(
        "peer inline-flex h-[2.1875rem] w-[4rem] shrink-0 items-center rounded-full pr-[0.5rem] pl-[0.345rem] shadow-xs outline-none disabled:cursor-not-allowed disabled:opacity-50",
        className
      )}
      style={
        isChecked
          ? {
              backgroundImage: "var(--color-gradient-main)",
            }
          : {
              border: "1px solid transparent",
              background: `linear-gradient(#fff, #fff) padding-box, var(--color-gradient-main) border-box`,
              backgroundOrigin: "border-box",
              backgroundClip: "padding-box, border-box",
            }
      }
      {...props}
    >
      <SwitchPrimitive.Thumb
        data-slot="switch-thumb"
        className={cn(
          "flex h-[1.625rem] w-[1.625rem] cursor-pointer items-center justify-center rounded-full bg-white ring-0 drop-shadow-2xl transition-transform data-[state=checked]:translate-x-[calc(100%-2px)] data-[state=unchecked]:translate-x-0"
        )}
      >
        {isChecked ? (
          <ArrowLeft className="h-[0.85638rem] w-[0.85638rem]" />
        ) : (
          <ArrowRight className="h-[0.85638rem] w-[0.85638rem]" />
        )}
      </SwitchPrimitive.Thumb>
    </SwitchPrimitive.Root>
  );
};

export { Switch };
