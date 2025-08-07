package com.seniclass.server.domain.auth.controller;

import com.seniclass.server.domain.auth.dto.*;
import com.seniclass.server.domain.auth.exception.errorcode.AuthErrorCode;
import com.seniclass.server.domain.auth.service.AuthService;
import com.seniclass.server.global.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/student")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse registerStudent(@Valid @RequestBody StudentRegisterRequest request) {
        return authService.registerStudent(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        if (token != null) {
            authService.logout(token);
        }
    }

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
