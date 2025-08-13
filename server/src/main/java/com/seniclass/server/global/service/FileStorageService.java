package com.seniclass.server.global.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/** 파일 저장 서비스 인터페이스 로컬 저장소와 S3 저장소를 추상화 */
public interface FileStorageService {

    /**
     * 파일을 저장하고 파일 경로/URL을 반환
     *
     * @param file 저장할 파일
     * @param subDirectory 저장할 하위 디렉토리
     * @return 저장된 파일의 경로 또는 URL
     */
    String storeFile(MultipartFile file, String subDirectory);

    /**
     * 파일을 삭제
     *
     * @param filePath 삭제할 파일의 경로
     */
    void deleteFile(String filePath);

    /**
     * 파일을 Resource로 로드
     *
     * @param filePath 로드할 파일의 경로
     * @return 파일 Resource
     */
    Resource loadAsResource(String filePath);

    /**
     * 파일의 public URL을 반환 (S3의 경우)
     *
     * @param filePath 파일 경로
     * @return public URL
     */
    String getFileUrl(String filePath);
}
