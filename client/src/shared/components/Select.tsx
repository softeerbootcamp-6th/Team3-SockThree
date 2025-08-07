import {
  Select as RadixSelect,
  SelectContent,
  SelectTrigger,
  SelectValue,
  SelectItem,
} from "@/shared/shadcn/ui/select";

interface SelectOption {
  value: string;
  label: string;
}

interface SelectProps {
  options: SelectOption[];
  placeholder?: string;
  value?: string;
  defaultValue?: string;
  onValueChange: (value: string) => void;
  className?: string;
  contentClassName?: string;
  disabled?: boolean;
  width?: string; // Tailwind width 클래스 (예: "w-40", "w-48")
}

const Select = ({
  options,
  placeholder = "선택해주세요",
  value,
  defaultValue,
  onValueChange,
  disabled = false,
}: SelectProps) => {
  return (
    <RadixSelect
      value={value}
      defaultValue={defaultValue}
      onValueChange={onValueChange}
      disabled={disabled}
    >
      <SelectTrigger
        className={`min-w-[6rem] cursor-pointer border border-gray-600 px-3`}
      >
        <SelectValue
          placeholder={placeholder}
          className="typo-body-5 min-w-max truncate border border-gray-600"
        />
      </SelectTrigger>
      <SelectContent
        className={`max-h-[200px] min-w-max overflow-y-auto border border-gray-600 bg-white text-gray-600`}
      >
        {options.map((option) => (
          <SelectItem
            key={option.value}
            value={option.value}
            className="typo-body-5 cursor-pointer hover:bg-gray-100 hover:text-black focus:bg-gray-200"
          >
            <span className="truncate">{option.label}</span>
          </SelectItem>
        ))}
      </SelectContent>
    </RadixSelect>
  );
};

export default Select;
