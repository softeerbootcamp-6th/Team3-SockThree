import { redirect } from "react-router";
import type { UserType } from "@/shared/types/auth";
import { token } from "@/shared/api/token";
import { validate } from "@/shared/api/auth";

export const checkAuth = async () => {
  const isToken = token.has();
  if (!isToken) {
    throw redirect("/login");
  }
  // @TODO validate에 따른 인증 로직 추가

  // @TODO validate응답에 인가 포함 로직 추가
  const userType: UserType = "student";
  return { userType };

  try {
    console.log("validate API 호출 시작");
    const res = await validate();
    console.log(res);
    const userType: UserType = "student";
    return { userType };
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
  } catch (_err) {
    token.clear();
    throw redirect("/login");
  }
};
