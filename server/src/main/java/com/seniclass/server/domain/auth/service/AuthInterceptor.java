package com.seniclass.server.domain.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seniclass.server.domain.auth.domain.AuthenticatedUser;
import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.auth.exception.errorcode.AuthErrorCode;
import com.seniclass.server.global.exception.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // OPTIONS 요청은 통과
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // HandlerMethod가 아닌 경우 통과 (정적 리소스 등)
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // @RequireAuth 어노테이션 확인
        RequireAuth requireAuth = handlerMethod.getMethodAnnotation(RequireAuth.class);
        if (requireAuth == null) {
            requireAuth = handlerMethod.getBeanType().getAnnotation(RequireAuth.class);
        }

        // 인증이 필요하지 않은 경우 통과
        if (requireAuth == null || !requireAuth.requireAuth()) {
            return true;
        }

        // 토큰 추출
        String token = extractTokenFromRequest(request);
        if (token == null) {
            sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    AuthErrorCode.UNAUTHORIZED,
                    "인증 토큰이 필요합니다");
            return false;
        }

        try {
            // 토큰 만료 체크를 먼저 수행
            if (jwtTokenProvider.isAccessTokenExpired(token)) {
                sendErrorResponse(
                        response,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        AuthErrorCode.ACCESS_TOKEN_EXPIRED,
                        "액세스 토큰이 만료되었습니다");
                return false;
            }

            // 토큰 유효성 검증
            if (!authService.isTokenValid(token)) {
                sendErrorResponse(
                        response,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        AuthErrorCode.INVALID_TOKEN,
                        "유효하지 않은 토큰입니다");
                return false;
            }

            // 사용자 정보 조회
            AuthenticatedUser currentUser = authService.getCurrentUser(token);

            // 역할 기반 인가 검증
            UserRole[] requiredRoles = requireAuth.roles();
            if (requiredRoles.length > 0) {
                boolean hasPermission =
                        Arrays.stream(requiredRoles)
                                .anyMatch(role -> role == currentUser.getRole());

                if (!hasPermission) {
                    sendErrorResponse(
                            response, HttpServletResponse.SC_FORBIDDEN, null, "접근 권한이 없습니다");
                    return false;
                }
            }

            // 요청에 사용자 정보 저장
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("userId", Long.parseLong(currentUser.getId().split("_")[1]));
            request.setAttribute("userRole", currentUser.getRole());

            return true;

        } catch (ExpiredJwtException e) {
            log.debug("JWT token expired", e);
            sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    AuthErrorCode.ACCESS_TOKEN_EXPIRED,
                    "액세스 토큰이 만료되었습니다");
            return false;
        } catch (Exception e) {
            log.error("Authentication error: ", e);
            sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    AuthErrorCode.INVALID_TOKEN,
                    "인증 처리 중 오류가 발생했습니다");
            return false;
        }
    }

    private void sendErrorResponse(
            HttpServletResponse response, int status, AuthErrorCode errorCode, String message)
            throws Exception {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse;
        if (errorCode != null) {
            errorResponse = ErrorResponse.of(errorCode.errorClassName(), errorCode.getMessage());
        } else {
            errorResponse = ErrorResponse.of("AuthError", message);
        }

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
