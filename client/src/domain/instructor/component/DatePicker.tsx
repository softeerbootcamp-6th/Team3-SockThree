import * as React from "react";
import { ko } from "date-fns/locale";
import { format } from "date-fns";

import { Button } from "@/shared/shadcn/ui/button";
import { Calendar } from "@/shared/shadcn/ui/calendar";
import {
  Popover,
  PopoverTrigger,
  PopoverContent,
} from "@/shared/shadcn/ui/popover";

type DatePickerProps = {
  value?: Date;
  onChange: (date: Date) => void;
  label?: string;
  placeholder?: string;
  id?: string;
  className?: string;
};

export const DatePicker = ({
  value,
  onChange,
  placeholder = "날짜를 선택하세요",
  id = "date-picker",
}: DatePickerProps) => {
  const [open, setOpen] = React.useState(false);

  const formatDate = (date: Date) => {
    return format(date, "yyyy년 MM월 dd일", { locale: ko });
  };

  return (
    <div className="flex flex-col gap-3">
      <Popover open={open} onOpenChange={setOpen}>
        <PopoverTrigger asChild>
          <Button
            variant="outline"
            id={id}
            className={`typo-body-5 w-48 cursor-pointer justify-between border-gray-400 ${
              value ? "text-gray-800" : "text-gray-400"
            }`}
          >
            {value ? formatDate(value) : placeholder}
          </Button>
        </PopoverTrigger>
        <PopoverContent
          className="w-auto overflow-hidden border-gray-400 bg-white p-0"
          align="start"
        >
          <Calendar
            mode="single"
            selected={value}
            captionLayout="label"
            locale={ko}
            onSelect={(selected) => {
              if (selected) {
                onChange(selected);
                setOpen(false);
              }
            }}
          />
        </PopoverContent>
      </Popover>
    </div>
  );
};
