import * as React from "react";
import { Slot } from "@radix-ui/react-slot";
import { cva, type VariantProps } from "class-variance-authority";
import { cn } from "@/lib/utils";

const circleButtonVariants = cva(
  "focus-visible:border-ring focus-visible:ring-ring/50 aria-invalid:ring-destructive/20 dark:aria-invalid:ring-destructive/40 aria-invalid:border-destructive inline-flex cursor-pointer items-center justify-center whitespace-nowrap transition-all outline-none focus-visible:ring-[3px] disabled:pointer-events-none disabled:bg-gray-200 disabled:bg-none disabled:text-gray-500 [&_svg]:shrink-0",
  {
    variants: {
      variant: {
        default:
          "typo-body-4 gap-1.5 rounded-[var(--radius-50)] bg-white text-black shadow-main duration-200 transition-all hover:h-[4.5625rem] hover:w-[10.625rem] hover:translate-x-[30%] hover:pl-4 [&_span]:hidden hover:[&_span]:inline-flex [&_svg]:h-[2.1219rem] [&_svg]:w-[2.1219rem] hover:[&_svg]:h-[2.625rem] hover:[&_svg]:w-[2.625rem]",
        gradient:
          "hover:translate-x-[30%] typo-body-5 border-circle-gradient-main hover:[&_span]:text-body-5 duration-200 transition-all bg-white text-black shadow-main hover:h-[3.7763rem] hover:w-[11.3281rem] hover:border-none hover:bg-gradient-main-diagonal [&_span]:hidden hover:[&_span]:inline-flex hover:[&_span]:text-white [&_svg]:h-[1.25rem] [&_svg]:w-[1.4375rem] hover:[&_svg]:hidden",
        ghost: "bg-transparent [&_svg]:h-[2.0625rem] [&_svg]:w-[2.375rem]",
      },
      size: {
        default: "h-[3.75rem] w-[3.75rem]",
      },
    },
    defaultVariants: {
      variant: "default",
      size: "default",
    },
  }
);

function CircleButton({
  className,
  variant,
  size,
  asChild = false,
  ...props
}: React.ComponentProps<"button"> &
  VariantProps<typeof circleButtonVariants> & {
    asChild?: boolean;
  }) {
  const Comp = asChild ? Slot : "button";

  return (
    <Comp
      data-slot="button"
      className={cn(circleButtonVariants({ variant, size, className }))}
      {...props}
    />
  );
}

export { CircleButton };
