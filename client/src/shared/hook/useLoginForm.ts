// hooks/useLoginForm.ts
import { useState } from "react";

interface FormErrors {
  email?: string;
  password?: string;
}

interface LoginFormData {
  email: string;
  password: string;
}

export const useLoginForm = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [formData, setFormData] = useState<LoginFormData>({
    email: "",
    password: "",
  });
  const [errors, setErrors] = useState<FormErrors>({});
  const [touched, setTouched] = useState<{ [key: string]: boolean }>({});

  const validateEmail = (email: string): string | undefined => {
    if (!email.trim()) return "이메일을 입력해주세요.";
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) return "올바른 이메일 형식이 아닙니다.";
    return undefined;
  };

  const validatePassword = (password: string): string | undefined => {
    if (!password) return "비밀번호를 입력해주세요.";
    if (password.length < 8) return "비밀번호는 8자 이상 입력해주세요.";
    return undefined;
  };

  const validateField = (field: keyof LoginFormData, value: string) => {
    switch (field) {
      case "email":
        return validateEmail(value);
      case "password":
        return validatePassword(value);
      default:
        return undefined;
    }
  };

  const handleInputChange = (field: keyof LoginFormData, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }));

    if (touched[field]) {
      const error = validateField(field, value);
      setErrors((prev) => ({
        ...prev,
        [field]: error,
      }));
    }
  };

  const handleInputBlur = (field: keyof LoginFormData) => {
    setTouched((prev) => ({ ...prev, [field]: true }));

    const error = validateField(field, formData[field]);
    setErrors((prev) => ({
      ...prev,
      [field]: error,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    setTouched({ email: true, password: true });

    const emailError = validateEmail(formData.email);
    const passwordError = validatePassword(formData.password);

    const newErrors: FormErrors = {};
    if (emailError) newErrors.email = emailError;
    if (passwordError) newErrors.password = passwordError;

    setErrors(newErrors);

    if (Object.keys(newErrors).length > 0) return;

    setIsLoading(true);
    try {
      console.log("Login data:", formData);
      // TODO: 실제 로그인 API 연동
    } catch (error) {
      console.error("Login error:", error);
      setErrors({ email: "이메일 또는 비밀번호가 올바르지 않습니다." });
    } finally {
      setIsLoading(false);
    }
  };

  const isFormValid =
    formData.email && formData.password && !errors.email && !errors.password;

  return {
    formData,
    errors,
    isLoading,
    isFormValid,
    handleInputChange,
    handleInputBlur,
    handleSubmit,
  };
};
