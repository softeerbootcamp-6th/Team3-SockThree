import { redirect } from "react-router";
import type { UserType } from "@/shared/types/auth";

export const checkAuth = async () => {
  const isAuthenticated = true; // @TODO: 인증 로직
  if (!isAuthenticated) throw redirect("/login");

  const userType: UserType = "student"; // @TODO: 권한 체크 로직

  return { userType };
};
