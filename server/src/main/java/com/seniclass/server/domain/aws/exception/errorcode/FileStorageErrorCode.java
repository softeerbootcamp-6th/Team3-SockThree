package com.seniclass.server.domain.aws.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FileStorageErrorCode implements BaseErrorCode {
    // 파일 검증 관련 에러
    FILE_EMPTY(HttpStatus.BAD_REQUEST, "업로드할 파일이 비어있습니다."),
    FILE_NAME_INVALID(HttpStatus.BAD_REQUEST, "파일명이 유효하지 않습니다."),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "파일 크기가 제한을 초과했습니다."),
    FILE_EXTENSION_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "허용되지 않는 파일 확장자입니다."),
    PATH_TRAVERSAL_DETECTED(HttpStatus.BAD_REQUEST, "경로 순회 공격이 감지되었습니다."),

    // S3 관련 에러
    S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "S3에 파일 업로드를 실패했습니다."),
    S3_DOWNLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "S3에서 파일 다운로드를 실패했습니다."),
    S3_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "S3에서 파일 삭제를 실패했습니다."),
    S3_PRESIGNED_URL_GENERATION_FAILED(
            HttpStatus.INTERNAL_SERVER_ERROR, "S3 Presigned URL 생성을 실패했습니다."),

    // 파일 I/O 관련 에러
    FILE_READ_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 읽기 중 오류가 발생했습니다."),
    FILE_WRITE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 쓰기 중 오류가 발생했습니다."),

    // 설정 관련 에러
    FILE_STORAGE_CONFIG_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 스토리지 설정 오류입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
