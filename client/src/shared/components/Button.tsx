import * as React from "react";
import { Slot } from "@radix-ui/react-slot";
import { cva, type VariantProps } from "class-variance-authority";
import { cn } from "@/lib/utils";

const buttonVariants = cva(
  "focus-visible:border-ring focus-visible:ring-ring/50 aria-invalid:ring-destructive/20 dark:aria-invalid:ring-destructive/40 aria-invalid:border-destructive inline-flex shrink-0 cursor-pointer items-center justify-center whitespace-nowrap transition-all outline-none focus-visible:ring-[3px] disabled:pointer-events-none disabled:bg-gray-200 disabled:bg-none disabled:text-gray-500 [&_svg]:pointer-events-none [&_svg]:shrink-0 [&_svg:not([class*='size-'])]:size-4",
  {
    variants: {
      variant: {
        default:
          "rounded-[15px] bg-gradient-main-diagonal-reverse text-white hover:bg-gradient-main-diagonal",
        outline:
          "border-gradient-main text-black shadow-main hover:text-main-500",
        ghost: "rounded-[5px] border border-gray-400 bg-transparent text-black",
        hoverUp:
          "rounded-[15px] border-gradient-main bg-white text-black transition-transform duration-300 ease-in-out hover:-translate-y-[18px] hover:bg-gray-100",
      },
      size: {
        lgFixed: "h-[4.5625rem] w-[20.375rem] px-[5.25rem] py-[1.25rem]",
        lgFlexible: "h-auto w-auto px-[5.25rem] py-[1.25rem]",
        md: "h-[4.0625rem] w-[16.125rem] px-[4.0625rem] py-[1.125rem]",
        sm: "typo-body-4 h-[4.375rem] w-[12rem] px-[1.375rem] py-[1.4375rem]",
        xs: "typo-label-3 h-[1.9375rem] w-[4.5rem] px-[.75rem] py-[.4375rem]",
      },
      textSize: {
        title2: "typo-title-2",
        title5: "typo-title-5",
      },
    },
    defaultVariants: {
      variant: "default",
      size: "lgFixed",
      textSize: "title5",
    },
  }
);

const Button = ({
  className,
  variant,
  size,
  textSize,
  asChild = false,
  ...props
}: React.ComponentProps<"button"> &
  VariantProps<typeof buttonVariants> & {
    asChild?: boolean;
  }) => {
  const Comp = asChild ? Slot : "button";

  return (
    <Comp
      data-slot="button"
      className={cn(
        buttonVariants({ variant, size, textSize }),
        className // 외부에서 주입한 override 클래스
      )}
      {...props}
    />
  );
};

export default Button;
