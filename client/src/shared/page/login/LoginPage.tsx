import LoginForm from "@/shared/components/LoginForm";

const LoginPage = () => {
  return (
    <div className="flex h-screen w-full items-center justify-center bg-bg p-6 md:p-10">
      <div className="flex w-full max-w-[40rem] flex-col gap-6 rounded-20 border border-gray-200 bg-white p-[3rem] shadow-main">
        <div className="flex flex-col gap-2">
          <h1 className="typo-title-5 text-gray-900">로그인</h1>
          <p className="typo-label-2 text-gray-600">
            이메일과 비밀번호를 입력해주세요
          </p>
        </div>
        <LoginForm />
        <div className="flex items-center justify-center gap-1 pt-2">
          <span className="typo-label-3 text-gray-500">계정이 없으신가요?</span>
          <a
            href="#"
            className="typo-label-3 text-main-600 transition-colors hover:text-main-700 hover:underline"
          >
            회원가입
          </a>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
