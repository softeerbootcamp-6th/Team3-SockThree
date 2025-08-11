package com.seniclass.server.global.service;

import com.seniclass.server.global.config.FileStorageProperties;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.GlobalErrorCode;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    private final FileStorageProperties fileStorageProperties;

    /** 파일을 로컬에 저장하고 파일 경로를 반환 */
    public String storeFile(MultipartFile file, String subDirectory) {
        // 파일명 정리
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // 파일 유효성 검증
            validateFile(file, originalFileName);

            // 업로드 디렉토리 생성
            Path uploadPath = createUploadDirectory(subDirectory);

            // 고유한 파일명 생성
            String storedFileName = generateUniqueFileName(originalFileName);

            // 파일 저장
            Path targetLocation = uploadPath.resolve(storedFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 상대 경로 반환 (DB에 저장할 경로)
            String relativePath = subDirectory + "/" + storedFileName;

            log.info("File stored successfully: {}", relativePath);
            return relativePath;

        } catch (IOException ex) {
            log.error("Could not store file {}", originalFileName, ex);
            throw new CommonException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /** 파일 삭제 */
    public void deleteFile(String filePath) {
        try {
            Path file = Paths.get(fileStorageProperties.getUploadDir()).resolve(filePath);
            Files.deleteIfExists(file);
            log.info("File deleted successfully: {}", filePath);
        } catch (IOException ex) {
            log.error("Could not delete file {}", filePath, ex);
        }
    }

    /** 파일을 Resource로 로드 */
    public Resource loadAsResource(String filePath) {
        try {
            Path file = Paths.get(fileStorageProperties.getUploadDir()).resolve(filePath);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new CommonException(GlobalErrorCode.NOT_FOUND);
            }
        } catch (MalformedURLException ex) {
            log.error("Could not read file: {}", filePath, ex);
            throw new CommonException(GlobalErrorCode.NOT_FOUND);
        }
    }

    /** 파일 유효성 검증 */
    private void validateFile(MultipartFile file, String fileName) {
        // 빈 파일 체크
        if (file.isEmpty()) {
            throw new CommonException(GlobalErrorCode.BAD_REQUEST);
        }

        // 파일 크기 체크
        if (file.getSize() > fileStorageProperties.getMaxFileSize()) {
            throw new CommonException(GlobalErrorCode.BAD_REQUEST);
        }

        // 파일명에 위험한 문자 포함 체크
        if (fileName.contains("..")) {
            throw new CommonException(GlobalErrorCode.BAD_REQUEST);
        }

        // 파일 확장자 체크
        String fileExtension = getFileExtension(fileName);
        if (!isAllowedExtension(fileExtension)) {
            throw new CommonException(GlobalErrorCode.BAD_REQUEST);
        }
    }

    /** 업로드 디렉토리 생성 */
    private Path createUploadDirectory(String subDirectory) throws IOException {
        Path uploadPath =
                Paths.get(fileStorageProperties.getUploadDir())
                        .resolve(subDirectory)
                        .toAbsolutePath()
                        .normalize();

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        return uploadPath;
    }

    /** 고유한 파일명 생성 */
    private String generateUniqueFileName(String originalFileName) {
        String fileExtension = getFileExtension(originalFileName);
        String baseName = getBaseName(originalFileName);
        String timestamp =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);

        return String.format("%s_%s_%s.%s", baseName, timestamp, uuid, fileExtension);
    }

    /** 파일 확장자 추출 */
    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf + 1).toLowerCase();
    }

    /** 파일명에서 확장자 제외한 부분 추출 */
    private String getBaseName(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return fileName;
        }
        return fileName.substring(0, lastIndexOf);
    }

    /** 허용된 확장자인지 확인 */
    private boolean isAllowedExtension(String extension) {
        return Arrays.asList(fileStorageProperties.getAllowedExtensions())
                .contains(extension.toLowerCase());
    }
}
