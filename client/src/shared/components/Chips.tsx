import { tv } from "tailwind-variants";
import * as React from "react";

const chipsVariant = tv({
  slots: {
    borderBase: "flex items-center justify-center cursor-pointer",
    border:
      "p-px bg-gray-400 text-gray-600 " +
      "transition-all hover:translate-y-[-12px] hover:shadow-md",
    base: "flex justify-center items-start bg-white",
    textContainer:
      "flex flex-col items-start justify-start gap-[2.5rem] text-gray-600",
    title: "",
    description: "whitespace-normal block max-w-[8.9rem] text-left break-words",
  },
  variants: {
    type: {
      day: {
        border: "rounded-[var(--radius-10)]",
        base: "rounded-[calc(var(--radius-10)-1px)]",
        textContainer: "px-[1.1rem] py-[1rem]",
      },
      level: {
        border: "rounded-[var(--radius-10)]",
        base: "rounded-[calc(var(--radius-10)-1px)]",
        textContainer: "px-[1.25rem] py-[1rem]",
      },
      field: {
        border: "rounded-[var(--radius-20)]",
        base: "rounded-[calc(var(--radius-20)-1px)]",
        textContainer: "px-[var(--spacing-28)] py-[var(--spacing-8)]",
      },
    },
    selected: {
      true: {
        border: "p-[1.5px] bg-gradient-to-b from-[#0850fd] to-[#26caba]",
        textContainer: "text-black",
        title: "typo-body-4",
        description: "typo-label-2",
      },
      false: {
        textContainer: "text-gray-600",
        title: "text-gray-600 typo-body-5",
        description: "text-gray-500 typo-label-2",
      },
    },
    interactive: {
      true: "",
      false: {
        borderBase: "bg-transparent pointer-events-none",
        border: "bg-transparent translate-y-0 shadow-none",
        base: "bg-white/30",
        textContainer: "text-white",
        title: "text-white",
      },
    },
  },
  defaultVariants: {
    selected: false,
    interactive: true,
  },
});

interface ChipsBaseProps
  extends Omit<React.HTMLAttributes<HTMLButtonElement>, "type"> {
  title: string;
  selected?: boolean;
  interactive?: boolean;
}

interface LevelChipsProps extends ChipsBaseProps {
  type: "level";
  description: string;
}

interface NonLevelChipsProps extends ChipsBaseProps {
  type: "day" | "field";
  description?: undefined;
}

export type ChipsProps = LevelChipsProps | NonLevelChipsProps;

const Chips = ({
  type,
  selected,
  interactive,
  title,
  description,
  ...props
}: ChipsProps) => {
  const {
    borderBase,
    base,
    border,
    textContainer,
    title: titleCls,
    description: descriptionCls,
  } = chipsVariant({
    type,
    selected,
    interactive,
  });

  return (
    <button className={borderBase()} {...props}>
      <div className={border()}>
        <div className={base()}>
          <div className={textContainer()}>
            <p className={titleCls()}>{title}</p>
            {type === "level" && (
              <p className={descriptionCls()}>{description}</p>
            )}
          </div>
        </div>
      </div>
    </button>
  );
};

export default Chips;
