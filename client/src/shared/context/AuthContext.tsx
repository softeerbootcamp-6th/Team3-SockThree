import React, { createContext } from "react";
import type { UserType } from "@/shared/types/auth";

export interface AuthContextType {
  userType: UserType;
}

export const AuthContext = createContext<AuthContextType | null>(null);

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
