package com.seniclass.server.domain.auth.controller;

import com.seniclass.server.domain.auth.dto.*;
import com.seniclass.server.domain.auth.exception.errorcode.AuthErrorCode;
import com.seniclass.server.domain.auth.service.AuthService;
import com.seniclass.server.global.exception.CommonException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Auth", description = "인증/인가 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입 (수강생)")
    @PostMapping(value = "/register/student", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse registerStudent(
            @Parameter(description = "수강생 회원가입에 필요한 정보") @Valid @RequestPart("request") StudentRegisterRequest request,
            @Parameter(description = "수강생 프로필 이미지") @RequestPart("file") MultipartFile file) {
        return authService.registerStudent(request, file);
    }

    @Operation(summary = "회원가입 (강사)")
    @PostMapping(value = "/register/teacher", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse registerTeacher(
            @Parameter(description = "강사 회원가입에 필요한 정보") @Valid @RequestPart("request") TeacherRegisterRequest request,
            @Parameter(description = "강사 프로필 이미지") @RequestPart("file") MultipartFile file) {
        return authService.registerTeacher(request, file);
    }

    @Operation(summary = "로그인 (공통)")
    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        return authService.login(request, response);
    }

    @Operation(summary = "토큰 재발급 (공통)")
    @PostMapping("/refresh")
    public TokenResponse refreshToken(
            @CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {
        return authService.refreshToken(refreshToken, response);
    }

    @Operation(summary = "로그아웃 (공통)")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = extractTokenFromRequest(request);
        if (token != null) {
            authService.logout(token);
        }
        authService.clearRefreshTokenCookie(response);
    }

    @Operation(summary = "토큰 유효성 검사 (공통)")
    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public void validateToken(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        if (token == null || !authService.isTokenValid(token)) {
            throw new CommonException(AuthErrorCode.UNAUTHORIZED);
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
