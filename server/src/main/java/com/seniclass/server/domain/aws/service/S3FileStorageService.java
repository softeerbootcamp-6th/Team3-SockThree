package com.seniclass.server.domain.aws.service;

import com.seniclass.server.global.config.FileStorageProperties;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.GlobalErrorCode;
import com.seniclass.server.global.service.FileStorageService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("s3FileStorageService")
@ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class S3FileStorageService implements FileStorageService {

    private final S3Service s3Service;
    private final FileStorageProperties fileStorageProperties;

    @Override
    public String storeFile(MultipartFile file, String subDirectory) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

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
            throw new CommonException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            log.error("S3 error while storing file {}: {}", originalFileName, ex.getMessage(), ex);
            throw new CommonException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            s3Service.delete(filePath);
            log.info("File deleted from S3: {}", filePath);

        } catch (Exception ex) {
            log.error("Could not delete file {} from S3: {}", filePath, ex.getMessage(), ex);
        }
    }

    @Override
    public Resource loadAsResource(String filePath) {
        try {
            byte[] data = s3Service.download(filePath);
            return new ByteArrayResource(data);
        } catch (Exception ex) {
            log.error("Could not load file {} from S3: {}", filePath, ex.getMessage(), ex);
            throw new CommonException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getFileUrl(String filePath) {
        // Presigned URL 생성 (1시간 유효)
        // Private 버킷에서도 접근 가능한 임시 URL 제공
        return s3Service.generatePresignedUrl(filePath);
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
            throw new CommonException(GlobalErrorCode.BAD_REQUEST);
        }

        if (file.getSize() > fileStorageProperties.getMaxFileSize()) {
            throw new CommonException(GlobalErrorCode.BAD_REQUEST);
        }

        if (fileName.contains("..")) {
            throw new CommonException(GlobalErrorCode.BAD_REQUEST);
        }

        String fileExtension = getFileExtension(fileName);
        if (!isAllowedExtension(fileExtension)) {
            throw new CommonException(GlobalErrorCode.BAD_REQUEST);
        }
    }

    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf + 1).toLowerCase();
    }

    private String getBaseName(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return fileName;
        }
        return fileName.substring(0, lastIndexOf);
    }

    private boolean isAllowedExtension(String extension) {
        return Arrays.asList(fileStorageProperties.getAllowedExtensions())
                .contains(extension.toLowerCase());
    }
}
