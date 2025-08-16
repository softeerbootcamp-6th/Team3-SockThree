import { Outlet, useLoaderData } from "react-router";
import { checkAuth } from "@/shared/auth/checkAuth";
import type { UserType } from "@/shared/types/auth";
import { AuthProvider } from "@/shared/context/AuthContext";

export const rootLoader = () => {
  return checkAuth();
};

const RootLayout = () => {
  const { userType } = useLoaderData<{ userType: UserType }>();
  return (
    <AuthProvider userType={userType}>
      <Outlet />
    </AuthProvider>
  );
};

export default RootLayout;
