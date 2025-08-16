import { useContext } from "react";
import {
  AuthContext,
  type AuthContextType,
} from "@/shared/context/AuthContext";

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error();
  }
  return context;
};
