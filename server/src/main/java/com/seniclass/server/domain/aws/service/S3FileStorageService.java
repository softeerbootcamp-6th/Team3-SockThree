package com.seniclass.server.domain.aws.service;

import com.seniclass.server.domain.aws.exception.errorcode.FileStorageErrorCode;
import com.seniclass.server.global.config.FileStorageProperties;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.service.FileStorageService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("s3FileStorageService")
@ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true")
@RequiredArgsConstructor
@Primary
@Slf4j
public class S3FileStorageService implements FileStorageService {

    private final S3Service s3Service;
    private final FileStorageProperties fileStorageProperties;

    @Override
    public String storeFile(MultipartFile file, String subDirectory) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new CommonException(FileStorageErrorCode.FILE_NAME_INVALID);
        }

        originalFileName = StringUtils.cleanPath(originalFileName);

        try {
            validateFile(file, originalFileName);
            String key = generateS3Key(subDirectory, originalFileName);

            // 기존 S3Service의 upload 메서드 사용
            s3Service.upload(key, file.getBytes(), file.getContentType());

            log.info("File stored in S3: {}", key);
            return key;

        } catch (CommonException ex) {
            throw ex;
        } catch (IOException ex) {
            log.error("Could not store file {} in S3", originalFileName, ex);
            throw new CommonException(FileStorageErrorCode.FILE_READ_ERROR);
        } catch (Exception ex) {
            log.error("S3 error while storing file {}: {}", originalFileName, ex.getMessage(), ex);
            throw new CommonException(FileStorageErrorCode.S3_UPLOAD_FAILED);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            s3Service.delete(filePath);
            log.info("File deleted from S3: {}", filePath);

        } catch (Exception ex) {
            log.error("Could not delete file {} from S3: {}", filePath, ex.getMessage(), ex);
            // 파일 삭제 실패는 로그만 남기고 예외를 던지지 않음 (기존 동작 유지)
            // 필요에 따라 throw new CommonException(FileStorageErrorCode.S3_DELETE_FAILED);로 변경 가능
        }
    }

    @Override
    public Resource loadAsResource(String filePath) {
        try {
            byte[] data = s3Service.download(filePath);
            return new ByteArrayResource(data);
        } catch (Exception ex) {
            log.error("Could not load file {} from S3: {}", filePath, ex.getMessage(), ex);
            throw new CommonException(FileStorageErrorCode.S3_DOWNLOAD_FAILED);
        }
    }

    @Override
    public String getFileUrl(String filePath) {
        try {
            // Presigned URL 생성 (1시간 유효)
            // Private 버킷에서도 접근 가능한 임시 URL 제공
            return s3Service.generatePresignedUrl(filePath);
        } catch (Exception ex) {
            log.error(
                    "Could not generate presigned URL for file {}: {}",
                    filePath,
                    ex.getMessage(),
                    ex);
            throw new CommonException(FileStorageErrorCode.S3_PRESIGNED_URL_GENERATION_FAILED);
        }
    }

    private String generateS3Key(String subDirectory, String originalFileName) {
        String fileExtension = getFileExtension(originalFileName);
        String baseName = getBaseName(originalFileName);
        String timestamp =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);

        String fileName = String.format("%s_%s_%s.%s", baseName, timestamp, uuid, fileExtension);
        return subDirectory + "/" + fileName;
    }

    private void validateFile(MultipartFile file, String fileName) {
        if (file.isEmpty()) {
            throw new CommonException(FileStorageErrorCode.FILE_EMPTY);
        }

        if (file.getSize() > fileStorageProperties.getMaxFileSize()) {
            throw new CommonException(FileStorageErrorCode.FILE_SIZE_EXCEEDED);
        }

        if (fileName == null || fileName.contains("..")) {
            throw new CommonException(FileStorageErrorCode.PATH_TRAVERSAL_DETECTED);
        }

        String fileExtension = getFileExtension(fileName);
        if (!isAllowedExtension(fileExtension)) {
            throw new CommonException(FileStorageErrorCode.FILE_EXTENSION_NOT_ALLOWED);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1 || lastIndexOf == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(lastIndexOf + 1).toLowerCase();
    }

    private String getBaseName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "unnamed";
        }

        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return fileName;
        }
        return fileName.substring(0, lastIndexOf);
    }

    private boolean isAllowedExtension(String extension) {
        String[] allowedExtensions = fileStorageProperties.getAllowedExtensions();
        if (allowedExtensions == null || extension == null) {
            throw new CommonException(FileStorageErrorCode.FILE_STORAGE_CONFIG_ERROR);
        }
        return Arrays.asList(allowedExtensions).contains(extension.toLowerCase());
    }
}
