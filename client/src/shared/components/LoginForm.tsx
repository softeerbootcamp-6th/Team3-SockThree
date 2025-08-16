// components/LoginForm.tsx
import Button from "@/shared/components/Button";
import { useLoginForm } from "@/shared/hook/useLoginForm";
import LoginInput from "@/shared/components/LoginInput";

const LoginForm = () => {
  const {
    formData,
    errors,
    isLoading,
    isFormValid,
    handleInputChange,
    handleInputBlur,
    handleSubmit,
  } = useLoginForm();

  return (
    <form className="flex flex-col gap-5" onSubmit={handleSubmit}>
      <LoginInput
        type="email"
        id="email"
        label="이메일"
        placeholder="example@email.com"
        value={formData.email}
        error={errors.email}
        onChange={(value) => handleInputChange("email", value)}
        onBlur={() => handleInputBlur("email")}
      />

      <LoginInput
        type="password"
        id="password"
        label="비밀번호"
        placeholder="비밀번호를 입력해주세요"
        value={formData.password}
        error={errors.password}
        onChange={(value) => handleInputChange("password", value)}
        onBlur={() => handleInputBlur("password")}
      />

      <div className="flex items-center justify-end">
        <a
          href="#"
          className="typo-label-4 text-main-600 transition-colors hover:text-main-700 hover:underline"
        >
          비밀번호를 잊으셨나요?
        </a>
      </div>

      <Button
        type="submit"
        variant="outline"
        size="lgFlexible"
        className="typo-label-0 flex h-12 items-center justify-center transition-all focus:ring-2 focus:ring-main-500/50 focus:outline-none disabled:cursor-not-allowed"
        disabled={!isFormValid || isLoading}
      >
        {isLoading ? "로그인 중..." : "로그인"}
      </Button>
    </form>
  );
};

export default LoginForm;
