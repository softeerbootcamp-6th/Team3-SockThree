import Button from "@/shared/components/Button";

interface FormError {
  email?: string;
  password?: string;
}

const LoginPage = () => {
  const errors: FormError = {
    email: "올바른 이메일을 입력하세요",
    password: "올바른 비밀번호를 입력해주세요",
  };

  return (
    <div className="flex h-screen w-full items-center justify-center bg-bg p-6 md:p-10">
      <div className="flex w-full max-w-[40rem] flex-col gap-6 rounded-20 border border-gray-200 bg-white p-[3rem] shadow-main">
        <div className="flex flex-col gap-2">
          <h1 className="typo-title-5 text-gray-900">로그인</h1>
          <p className="typo-label-2 text-gray-600">
            이메일과 비밀번호를 입력해주세요
          </p>
        </div>

        <form className="flex flex-col gap-5" onSubmit={() => {}}>
          <div className="flex flex-col gap-2">
            <div className="flex items-center justify-between">
              <label className="typo-label-1 text-gray-800" htmlFor="email">
                이메일
              </label>
              {errors.email && (
                <span className="typo-label-4 text-red-500">
                  올바른 이메일을 입력하세요
                </span>
              )}
            </div>
            <input
              type="email"
              id="email"
              placeholder="example@email.com"
              className={`typo-label-2 flex h-16 w-full rounded-10 border bg-white px-[0.5rem] py-5 text-gray-900 transition-colors placeholder:text-gray-400 focus:outline-none disabled:cursor-not-allowed disabled:opacity-50 ${
                errors.email
                  ? "border-red-500 focus:border-red-500 focus:ring-2 focus:ring-red-500/20"
                  : "border-gray-300 focus:border-main-500 focus:ring-2 focus:ring-main-500/20"
              }`}
              value="이메일"
              onChange={() => {}}
              onBlur={() => {}}
            />
          </div>

          <div className="flex flex-col gap-2">
            <div className="flex items-center justify-between">
              <label className="typo-label-1 text-gray-800" htmlFor="password">
                비밀번호
              </label>
              {errors.password && (
                <span className="typo-label-4 text-red-500">
                  올바른 비밀번호를 입력해주세요
                </span>
              )}
            </div>
            <input
              type="password"
              id="password"
              placeholder="비밀번호를 입력해주세요"
              className={`typo-label-2 flex h-16 w-full rounded-10 border bg-white px-[0.5rem] py-5 text-gray-900 transition-colors placeholder:text-gray-400 focus:outline-none disabled:cursor-not-allowed disabled:opacity-50 ${
                errors.password
                  ? "border-red-500 focus:border-red-500 focus:ring-2 focus:ring-red-500/20"
                  : "border-gray-300 focus:border-main-500 focus:ring-2 focus:ring-main-500/20"
              }`}
              onChange={() => {}}
              onBlur={() => {}}
            />
          </div>

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
            disabled={false}
          ></Button>

          <div className="flex items-center justify-center gap-1 pt-2">
            <span className="typo-label-3 text-gray-500">
              계정이 없으신가요?
            </span>
            <a
              href="#"
              className="typo-label-3 text-main-600 transition-colors hover:text-main-700 hover:underline"
            >
              회원가입
            </a>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
