import React from "react";
import { tv } from "tailwind-variants";

// icons components
import SuccessIcon from "@/assets/icons/default/success-check.svg?react";
import WarningIcon from "@/assets/icons/default/warning-mark.svg?react";

const inputVariants = tv({
  slots: {
    wrapper: "flex flex-col gap-1 py-1",
    label: "typo-label-1 text-gray-700",
    input:
      "w-full border p-[var(--spacing-12)] rounded-[15px] outline-none transition placeholder:text-gray-400 bg-white",
    message: "typo-label-4 flex gap-1.5",
    counter: "typo-label-4 text-gray-400 absolute right-3 bottom-3.5",
  },
  variants: {
    status: {
      default: {
        input: "border-gray-300 focus:border-gray-900",
        message: "hidden",
        counter: "",
      },
      success: {
        input: "border-blue-500 text-black",
        message: "text-blue-500",
      },
      warning: {
        input: "border-red-500 text-red-600",
        message: "text-red-500",
      },
    },
    size: {
      sm: { wrapper: "w-[23.5rem]" },
      md: { wrapper: "w-[37rem]" },
      lg: { wrapper: "w-[65rem]" },
      full: { wrapper: "w-full" },
      fit: { wrapper: "w-fit" },
    },
  },
  defaultVariants: {
    status: "default",
    size: "sm",
  },
});

type InputStatus = "default" | "success" | "warning";

export interface InputProps
  extends Omit<React.InputHTMLAttributes<HTMLInputElement>, "value"> {
  label?: string;
  status?: InputStatus;
  successMessage?: string;
  errorMessage?: string;
  value: string; // required로 지정
  maxLength?: number;
  type?: "text" | "number" | "date";
  inputRef?: React.Ref<HTMLInputElement>;
}

export const Input = ({
  label,
  status = "default",
  successMessage = "successMessage",
  errorMessage = "errorMessage",
  value,
  maxLength,
  type = "text",
  inputRef,
  ...rest
}: InputProps) => {
  const {
    wrapper,
    label: labelCls,
    input,
    message,
    counter,
  } = inputVariants({ status });

  return (
    <div className={wrapper()}>
      <div className="flex w-full items-center justify-between">
        <div>{label && <label className={labelCls()}>{label}</label>}</div>
        <div className={message()}>
          {status === "warning" && (
            <>
              <WarningIcon width={16} /> <p> {errorMessage}</p>
            </>
          )}
          {status === "success" && (
            <>
              <SuccessIcon width={16} /> <p>{successMessage}</p>
            </>
          )}
        </div>
      </div>
      <div className="relative w-full">
        <input
          ref={inputRef}
          type={type}
          value={value}
          maxLength={maxLength}
          className={input()}
          {...rest}
        />
        {maxLength !== undefined && (
          <p className={counter()}>
            {value.length} / {maxLength}
          </p>
        )}
      </div>
    </div>
  );
};
