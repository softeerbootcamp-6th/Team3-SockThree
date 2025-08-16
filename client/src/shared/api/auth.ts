import { createApi } from "@/shared/api/createApi";
import type { Components } from "@/shared/types/openapi-type";

/* endpoint 정의 */
const authApi = createApi("/auth");

/* Auth 관련 타입 정의 */
type StudentRegisterRequest = Components["schemas"]["StudentRegisterRequest"];
type TeacherRegisterRequest = Components["schemas"]["TeacherRegisterRequest"];
type RegisterResponse = Components["schemas"]["RegisterResponse"];
type LoginRequest = Components["schemas"]["LoginRequest"];
type LoginResponse = Components["schemas"]["LoginResponse"];
type TokenResponse = Components["schemas"]["TokenResponse"];

// 학생 회원가입
export const registerStudent = (data: StudentRegisterRequest) =>
  authApi.post<RegisterResponse, StudentRegisterRequest>(
    "/register/student",
    data
  );

// 강사 회원가입
export const registerTeacher = (data: TeacherRegisterRequest) =>
  authApi.post<RegisterResponse, TeacherRegisterRequest>(
    "/register/teacher",
    data
  );

// 로그인
export const login = (data: LoginRequest) =>
  authApi.post<LoginResponse, LoginRequest>("/login", data);

// 로그아웃
export const logout = (signal?: AbortSignal) =>
  authApi.post<void, void>("/logout", undefined, { signal });

// 토큰 갱신
export const refresh = (signal?: AbortSignal) =>
  authApi.post<TokenResponse, void>("/refresh", undefined, { signal });

// 토큰 검증
export const validate = (signal?: AbortSignal) =>
  authApi.get<void>("/validate", undefined, { signal });
