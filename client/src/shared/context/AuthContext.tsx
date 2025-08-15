import React, { createContext, useContext } from "react";
import type { UserType } from "@/shared/types/auth";

interface AuthContextType {
  userType: UserType;
}

const AuthContext = createContext<AuthContextType | null>(null);

interface AuthProviderProps {
  children: React.ReactNode;
  userType: UserType;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({
  children,
  userType,
}) => {
  return (
    <AuthContext.Provider value={{ userType }}>{children}</AuthContext.Provider>
  );
};

// Hook 생성
export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error();
  }
  return context;
};
