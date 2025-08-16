import { Outlet, useLoaderData } from "react-router";
import { checkAuth } from "@/shared/auth/checkAuth";
import type { UserType } from "@/shared/types/auth";
import { AuthProvider } from "@/shared/context/AuthContext";
import { QueryClientProvider } from "@tanstack/react-query";
import { queryClient } from "@/shared/utils/queryClient";

export const rootLoader = () => {
  return checkAuth();
};

const RootLayout = () => {
  const { userType } = useLoaderData<{ userType: UserType }>();
  return (
    <QueryClientProvider client={queryClient}>
      <AuthProvider userType={userType}>
        <Outlet />
      </AuthProvider>
    </QueryClientProvider>
  );
};

export default RootLayout;
