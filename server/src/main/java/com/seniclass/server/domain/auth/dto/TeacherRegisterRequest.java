package com.seniclass.server.domain.auth.dto;

import com.seniclass.server.domain.student.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.util.List;

@Schema(description = "강사 회원가입 요청")
public record TeacherRegisterRequest(
        @Schema(description = "이름", example = "홍길동")
                @NotBlank(message = "이름은 필수입니다")
                @Size(min = 2, max = 10, message = "이름은 2~10자 사이여야 합니다")
                String name,
        @Schema(description = "이메일", example = "student@example.com")
                @NotBlank(message = "이메일은 필수입니다")
                @Email(message = "올바른 이메일 형식이 아닙니다")
                String email,
        @Schema(description = "나이", example = "20")
                @NotNull(message = "나이는 필수입니다")
                @Min(value = 18, message = "나이는 18세 이상이어야 합니다")
                @Max(value = 100, message = "나이는 100세 이하여야 합니다")
                Integer age,
        @Schema(
                        description = "성별",
                        example = "MALE",
                        allowableValues = {"MALE", "FEMALE"})
                @NotNull(message = "성별은 필수입니다")
                Gender gender,
        @Schema(description = "비밀번호", example = "password123!")
                @NotBlank(message = "비밀번호는 필수입니다")
                @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다")
                @Pattern(
                        regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                        message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다")
                String password,
        @Schema(description = "비밀번호 확인", example = "password123!")
                @NotBlank(message = "비밀번호 확인은 필수입니다")
                String passwordConfirm,
        @Schema(description = "강사 소개", example = "같이 골프 배워보아요!")
                @NotBlank(message = "강사 소개는 필수입니다")
                @Size(max = 50, message = "강사 소개는 최대 80자입니다")
                String instruction,
        @Schema(description = "강사 이력") List<CareerRegisterRequest> careerList) {

    public boolean isPasswordMatching() {
        return password != null && password.equals(passwordConfirm);
    }
}
