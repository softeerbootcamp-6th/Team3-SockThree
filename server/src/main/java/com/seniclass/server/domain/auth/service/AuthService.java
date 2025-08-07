package com.seniclass.server.domain.auth.service;

import com.seniclass.server.domain.auth.domain.AuthenticatedUser;
import com.seniclass.server.domain.auth.dto.*;
import com.seniclass.server.domain.auth.exception.errorcode.AuthErrorCode;
import com.seniclass.server.domain.teacher.domain.Teacher;
import com.seniclass.server.domain.teacher.service.CareerService;
import com.seniclass.server.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordService passwordService;
    private final TokenManagementService tokenManagementService;
    private final UserAuthenticationService userAuthenticationService;
    private final CareerService careerService;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        AuthenticatedUser user =
                userAuthenticationService.authenticate(request.email(), request.password());
        tokenManagementService.revokeAllUserRefreshTokens(user.getId());
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        tokenManagementService.saveRefreshToken(refreshToken, user.getId());
        log.info("User logged in successfully: {}", user.getEmail());
        return new LoginResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtTokenProvider.getAccessTokenValidityInMilliseconds() / 1000,
                new LoginResponse.UserInfo(
                        user.getId(), user.getEmail(), user.getRole().getValue()));
    }

    @Transactional
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
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
        log.info("Token refreshed for user: {}", user.getEmail());
        return new TokenResponse(
                newAccessToken,
                newRefreshToken,
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
    public RegisterResponse registerStudent(StudentRegisterRequest request) {
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
                        encodedPassword);
        log.info("Student registered successfully: {} ({})", request.name(), request.email());
        return new RegisterResponse(
                userId, request.name(), request.email(), "STUDENT", "회원가입이 완료되었습니다");
    }

    @Transactional
    public RegisterResponse registerTeacher(TeacherRegisterRequest request) {
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
                        request.instruction());

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
