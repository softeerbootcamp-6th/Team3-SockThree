// components/LoginInput.tsx
interface LoginInputProps {
  type: "email" | "password";
  id: string;
  label: string;
  placeholder: string;
  value: string;
  error?: string;
  onChange: (value: string) => void;
  onBlur: () => void;
}

const LoginInput = ({
  type,
  id,
  label,
  placeholder,
  value,
  error,
  onChange,
  onBlur,
}: LoginInputProps) => {
  return (
    <div className="flex flex-col gap-2">
      <div className="flex items-center justify-between">
        <label className="typo-label-1 text-gray-800" htmlFor={id}>
          {label}
        </label>
        {error && (
          <span className="typo-label-4 text-red-500">
            {type === "email"
              ? "올바른 이메일을 입력하세요"
              : "올바른 비밀번호를 입력해주세요"}
          </span>
        )}
      </div>
      <input
        type={type}
        id={id}
        placeholder={placeholder}
        className={`typo-label-2 flex h-16 w-full rounded-10 border bg-white px-[0.5rem] py-5 text-gray-900 transition-colors placeholder:text-gray-400 focus:outline-none disabled:cursor-not-allowed disabled:opacity-50 ${
          error
            ? "border-red-500 focus:border-red-500 focus:ring-2 focus:ring-red-500/20"
            : "border-gray-300 focus:border-main-500 focus:ring-2 focus:ring-main-500/20"
        }`}
        value={value}
        onChange={(e) => onChange(e.target.value)}
        onBlur={onBlur}
      />
    </div>
  );
};

export default LoginInput;
