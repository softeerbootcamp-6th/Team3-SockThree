import { Outlet, useLoaderData } from "react-router";
import { checkAuth } from "@/shared/auth/checkAuth";
import type { UserType } from "@/shared/types/auth";
import { AuthProvider } from "@/shared/context/AuthContext";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 5 * 60 * 1000, // 5분
      retry: 1,
    },
  },
});

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
