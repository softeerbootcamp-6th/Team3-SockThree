package com.seniclass.server.domain.aws.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.seniclass.server.global.config.FileStorageProperties;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.GlobalErrorCode;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class S3FileStorageServiceTest {

    @InjectMocks private S3FileStorageService s3FileStorageService;

    @Mock private S3Service s3Service;

    @Mock private FileStorageProperties fileStorageProperties;

    @Mock private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        // lenient stubbing을 사용하여 불필요한 stubbing 경고 방지
        lenient().when(fileStorageProperties.getMaxFileSize()).thenReturn(10485760L); // 10MB
        lenient()
                .when(fileStorageProperties.getAllowedExtensions())
                .thenReturn(new String[] {"pdf", "doc", "docx", "txt", "zip", "hwp"});
    }

    @Test
    @DisplayName("파일 저장 성공")
    void storeFile_Success() throws IOException {
        // given
        String originalFileName = "test.pdf";
        String subDirectory = "assignments";
        byte[] fileBytes = "test content".getBytes();
        String contentType = "application/pdf";

        when(mockFile.getOriginalFilename()).thenReturn(originalFileName);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1000L);
        when(mockFile.getBytes()).thenReturn(fileBytes);
        when(mockFile.getContentType()).thenReturn(contentType);
        when(s3Service.upload(anyString(), any(byte[].class), anyString()))
                .thenReturn("mocked-key");

        // when
        String result = s3FileStorageService.storeFile(mockFile, subDirectory);

        // then
        assertNotNull(result);
        assertTrue(result.startsWith(subDirectory + "/"));
        assertTrue(result.contains("test"));
        assertTrue(result.endsWith(".pdf"));

        verify(s3Service, times(1)).upload(anyString(), eq(fileBytes), eq(contentType));
    }

    @Test
    @DisplayName("파일 저장 실패 - 빈 파일")
    void storeFile_EmptyFile() {
        // given
        String originalFileName = "test.pdf";
        String subDirectory = "assignments";

        when(mockFile.getOriginalFilename()).thenReturn(originalFileName);
        when(mockFile.isEmpty()).thenReturn(true);

        // when & then
        CommonException exception =
                assertThrows(
                        CommonException.class,
                        () -> s3FileStorageService.storeFile(mockFile, subDirectory));

        assertEquals(GlobalErrorCode.BAD_REQUEST, exception.getErrorCode());
        verify(s3Service, never()).upload(anyString(), any(byte[].class), anyString());
    }

    @Test
    @DisplayName("파일 저장 실패 - 파일 크기 초과")
    void storeFile_FileSizeExceeded() {
        // given
        String originalFileName = "test.pdf";
        String subDirectory = "assignments";

        when(mockFile.getOriginalFilename()).thenReturn(originalFileName);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(20971520L); // 20MB

        // when & then
        CommonException exception =
                assertThrows(
                        CommonException.class,
                        () -> s3FileStorageService.storeFile(mockFile, subDirectory));

        assertEquals(GlobalErrorCode.BAD_REQUEST, exception.getErrorCode());
        verify(s3Service, never()).upload(anyString(), any(byte[].class), anyString());
    }

    @Test
    @DisplayName("파일 저장 실패 - 허용되지 않는 확장자")
    void storeFile_InvalidExtension() {
        // given
        String originalFileName = "test.exe";
        String subDirectory = "assignments";

        when(mockFile.getOriginalFilename()).thenReturn(originalFileName);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1000L);

        // when & then
        CommonException exception =
                assertThrows(
                        CommonException.class,
                        () -> s3FileStorageService.storeFile(mockFile, subDirectory));

        assertEquals(GlobalErrorCode.BAD_REQUEST, exception.getErrorCode());
        verify(s3Service, never()).upload(anyString(), any(byte[].class), anyString());
    }

    @Test
    @DisplayName("파일 저장 실패 - 경로 트래버설 공격")
    void storeFile_PathTraversal() {
        // given
        String originalFileName = "../../../malicious.pdf";
        String subDirectory = "assignments";

        when(mockFile.getOriginalFilename()).thenReturn(originalFileName);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1000L);

        // when & then
        CommonException exception =
                assertThrows(
                        CommonException.class,
                        () -> s3FileStorageService.storeFile(mockFile, subDirectory));

        assertEquals(GlobalErrorCode.BAD_REQUEST, exception.getErrorCode());
        verify(s3Service, never()).upload(anyString(), any(byte[].class), anyString());
    }

    @Test
    @DisplayName("파일 삭제 성공")
    void deleteFile_Success() {
        // given
        String filePath = "assignments/test.pdf";

        // when
        s3FileStorageService.deleteFile(filePath);

        // then
        verify(s3Service, times(1)).delete(filePath);
    }

    @Test
    @DisplayName("파일 삭제 실패 - S3 오류")
    void deleteFile_S3Error() {
        // given
        String filePath = "assignments/test.pdf";
        doThrow(new RuntimeException("S3 error")).when(s3Service).delete(filePath);

        // when & then (예외가 발생하지 않고 로그만 출력됨)
        assertDoesNotThrow(() -> s3FileStorageService.deleteFile(filePath));

        verify(s3Service, times(1)).delete(filePath);
    }

    @Test
    @DisplayName("파일 리소스 로드 성공")
    void loadAsResource_Success() {
        // given
        String filePath = "assignments/test.pdf";
        byte[] fileData = "test content".getBytes();

        when(s3Service.download(filePath)).thenReturn(fileData);

        // when
        Resource resource = s3FileStorageService.loadAsResource(filePath);

        // then
        assertNotNull(resource);
        verify(s3Service, times(1)).download(filePath);
    }

    @Test
    @DisplayName("파일 리소스 로드 실패 - S3 오류")
    void loadAsResource_S3Error() {
        // given
        String filePath = "assignments/test.pdf";
        when(s3Service.download(filePath)).thenThrow(new RuntimeException("S3 error"));

        // when & then
        CommonException exception =
                assertThrows(
                        CommonException.class, () -> s3FileStorageService.loadAsResource(filePath));

        assertEquals(GlobalErrorCode.INTERNAL_SERVER_ERROR, exception.getErrorCode());
        verify(s3Service, times(1)).download(filePath);
    }

    @Test
    @DisplayName("파일 URL 생성 성공 - Presigned URL")
    void getFileUrl_Success() {
        // given
        String filePath = "assignments/test.pdf";
        String expectedPresignedUrl =
                "https://test-bucket.s3.ap-northeast-2.amazonaws.com/assignments/test.pdf?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20250812T000000Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAIOSFODNN7EXAMPLE%2F20250812%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=example";
        when(s3Service.generatePresignedUrl(filePath)).thenReturn(expectedPresignedUrl);

        // when
        String fileUrl = s3FileStorageService.getFileUrl(filePath);

        // then
        assertEquals(expectedPresignedUrl, fileUrl);
        verify(s3Service, times(1)).generatePresignedUrl(filePath);
    }

    @Test
    @DisplayName("파일 저장 실패 - IOException")
    void storeFile_IOException() throws IOException {
        // given
        String originalFileName = "test.pdf";
        String subDirectory = "assignments";

        when(mockFile.getOriginalFilename()).thenReturn(originalFileName);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1000L);
        when(mockFile.getBytes()).thenThrow(new IOException("File read error"));

        // when & then
        CommonException exception =
                assertThrows(
                        CommonException.class,
                        () -> s3FileStorageService.storeFile(mockFile, subDirectory));

        assertEquals(GlobalErrorCode.INTERNAL_SERVER_ERROR, exception.getErrorCode());
        verify(s3Service, never()).upload(anyString(), any(byte[].class), anyString());
    }
}
