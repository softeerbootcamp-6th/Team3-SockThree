package com.seniclass.server.domain.auth.service;

import com.seniclass.server.domain.auth.domain.AuthenticatedUser;
import com.seniclass.server.domain.auth.dto.*;
import com.seniclass.server.domain.auth.exception.errorcode.AuthErrorCode;
import com.seniclass.server.domain.teacher.domain.Teacher;
import com.seniclass.server.domain.teacher.service.CareerService;
import com.seniclass.server.global.exception.CommonException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordService passwordService;
    private final TokenManagementService tokenManagementService;
    private final UserAuthenticationService userAuthenticationService;
    private final CareerService careerService;

    @Value("${app.cookie.secure:false}")
    private boolean cookieSecure;

    @Transactional
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        AuthenticatedUser user =
                userAuthenticationService.authenticate(request.email(), request.password());
        tokenManagementService.revokeAllUserRefreshTokens(user.getId());
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        tokenManagementService.saveRefreshToken(refreshToken, user.getId());

        // Refresh token을 HttpOnly 쿠키로 설정
        setRefreshTokenCookie(response, refreshToken);

        log.info("User logged in successfully: {}", user.getEmail());
        return new LoginResponse(
                accessToken,
                "Bearer",
                jwtTokenProvider.getAccessTokenValidityInMilliseconds() / 1000,
                new LoginResponse.UserInfo(
                        user.getId(), user.getEmail(), user.getRole().getValue()));
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie =
                ResponseCookie.from("refresh_token", refreshToken)
                        .maxAge(jwtTokenProvider.getRefreshTokenValidityInMilliseconds() / 1000)
                        .path("/")
                        .httpOnly(true)
                        .secure(cookieSecure) // 환경에 따라 설정
                        .sameSite(cookieSecure ? "None" : "Lax")
                        .build(); // HTTPS면 None, HTTP면 Lax
        response.addHeader("Set-Cookie", cookie.toString());
        log.info("Set refresh token cookie for user: {}", "***masked***");
    }

    public void clearRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie =
                ResponseCookie.from("refresh_token", "")
                        .maxAge(0)
                        .path("/")
                        .httpOnly(true)
                        .secure(cookieSecure) // 환경에 따라 설정
                        .sameSite(cookieSecure ? "None" : "Lax")
                        .build();
        response.addHeader("Set-Cookie", cookie.toString());
        log.info("Cleared refresh token cookie");
    }

    @Transactional
    public TokenResponse refreshToken(String refreshToken, HttpServletResponse response) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new CommonException(AuthErrorCode.INVALID_TOKEN);
        }

        if (!jwtTokenProvider.validateToken(refreshToken)
                || !tokenManagementService.isValidRefreshToken(refreshToken)) {
            throw new CommonException(AuthErrorCode.INVALID_TOKEN);
        }

        String userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
        AuthenticatedUser user = userAuthenticationService.findByIdAndValidate(userId);
        tokenManagementService.revokeRefreshToken(refreshToken);
        String newAccessToken = jwtTokenProvider.generateAccessToken(user);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);
        tokenManagementService.saveRefreshToken(newRefreshToken, user.getId());

        // 새로운 refresh token을 쿠키로 설정
        setRefreshTokenCookie(response, newRefreshToken);

        log.info("Token refreshed for user: {}", user.getEmail());
        return new TokenResponse(
                newAccessToken,
                "Bearer",
                jwtTokenProvider.getAccessTokenValidityInMilliseconds() / 1000);
    }

    @Transactional
    public void logout(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new CommonException(AuthErrorCode.INVALID_TOKEN);
        }
        String userId = jwtTokenProvider.getUserIdFromToken(accessToken);
        tokenManagementService.addToBlacklist(accessToken, userId);
        tokenManagementService.revokeAllUserRefreshTokens(userId);
        log.info("User logged out successfully: {}", userId);
    }

    public boolean isTokenValid(String token) {
        return jwtTokenProvider.validateToken(token)
                && !tokenManagementService.isBlacklisted(token);
    }

    public AuthenticatedUser getCurrentUser(String token) {
        if (!isTokenValid(token)) {
            throw new CommonException(AuthErrorCode.INVALID_TOKEN);
        }
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        return userAuthenticationService.findByIdAndValidate(userId);
    }

    @Transactional
    public RegisterResponse registerStudent(
            StudentRegisterRequest request, MultipartFile profileImage) {
        if (!request.isPasswordMatching()) {
            throw new CommonException(AuthErrorCode.PASSWORD_MISMATCH);
        }
        if (userAuthenticationService.existsByEmail(request.email())) {
            throw new CommonException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }
        String encodedPassword = passwordService.encodePassword(request.password());
        String userId =
                userAuthenticationService.createStudent(
                        request.name(),
                        request.email(),
                        request.age(),
                        request.gender(),
                        encodedPassword,
                        profileImage);
        log.info("Student registered successfully: {} ({})", request.name(), request.email());
        return new RegisterResponse(
                userId, request.name(), request.email(), "STUDENT", "회원가입이 완료되었습니다");
    }

    @Transactional
    public RegisterResponse registerTeacher(
            TeacherRegisterRequest request, MultipartFile profileImage) {
        if (!request.isPasswordMatching()) {
            throw new CommonException(AuthErrorCode.PASSWORD_MISMATCH);
        }
        if (userAuthenticationService.existsByEmail(request.email())) {
            throw new CommonException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }
        String encodedPassword = passwordService.encodePassword(request.password());
        Teacher savedTeacher =
                userAuthenticationService.createTeacher(
                        request.name(),
                        request.email(),
                        request.age(),
                        request.gender(),
                        encodedPassword,
                        request.instruction(),
                        profileImage);

        for (CareerRegisterRequest registerRequest : request.careerList()) {
            careerService.createCareer(registerRequest, savedTeacher);
        }

        log.info(
                "Teacher registered successfully: {} ({})",
                savedTeacher.getName(),
                savedTeacher.getEmail());
        return new RegisterResponse(
                savedTeacher.getId().toString(),
                savedTeacher.getName(),
                savedTeacher.getEmail(),
                "TEACHER",
                "회원가입이 완료되었습니다");
    }
}
