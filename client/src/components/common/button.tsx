import * as React from "react";
import { Slot } from "@radix-ui/react-slot";
import { cva, type VariantProps } from "class-variance-authority";
import { cn } from "@/lib/utils";

const buttonVariants = cva(
  "focus-visible:border-ring focus-visible:ring-ring/50 aria-invalid:ring-destructive/20 dark:aria-invalid:ring-destructive/40 aria-invalid:border-destructive inline-flex shrink-0 items-center justify-center whitespace-nowrap transition-all outline-none focus-visible:ring-[3px] disabled:pointer-events-none disabled:bg-gray-200 disabled:bg-none disabled:text-gray-500 [&_svg]:pointer-events-none [&_svg]:shrink-0 [&_svg:not([class*='size-'])]:size-4",
  {
    variants: {
      variant: {
        default:
          "rounded-[15px] bg-gradient-main-diagonal-reverse text-white hover:bg-gradient-main-diagonal",
        outline:
          "border-gradient-main text-black shadow-main hover:text-main-500",
      },
      size: {
        fixed: "h-[4.5625rem] w-[20.375rem] px-[5.25rem] py-[1.25rem]",
        flexible: "h-auto w-auto px-[5.25rem] py-[1.25rem]",
        icon: "size-9",
      },
      textSize: {
        title2: "text-title-2",
        title5: "title-2",
      },
    },
    defaultVariants: {
      variant: "default",
      size: "fixed",
      textSize: "title5",
    },
  }
);

function Button({
  className,
  variant,
  size,
  textSize,
  asChild = false,
  ...props
}: React.ComponentProps<"button"> &
  VariantProps<typeof buttonVariants> & {
    asChild?: boolean;
  }) {
  const Comp = asChild ? Slot : "button";

  return (
    <Comp
      data-slot="button"
      className={cn(buttonVariants({ variant, size, textSize, className }))}
      {...props}
    />
  );
}

export { Button, buttonVariants };
