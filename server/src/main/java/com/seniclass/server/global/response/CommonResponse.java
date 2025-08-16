package com.seniclass.server.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "공통 응답")
public class CommonResponse<T> {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "성공 여부")
    private boolean success;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "상태 코드")
    private int status;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "데이터")
    private T data;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "타임스탬프")
    private LocalDateTime timestamp;

    public static <T> CommonResponse<T> onSuccess(int status, T data) {
        return CommonResponse.<T>builder()
                .success(true)
                .status(status)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> CommonResponse<T> onFailure(int status, T data) {
        return CommonResponse.<T>builder()
                .success(false)
                .status(status)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
