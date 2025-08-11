package com.seniclass.server.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileStorageProperties {

    private String uploadDir = "uploads/assignments";
    private long maxFileSize = 10485760; // 10MB
    private String[] allowedExtensions = {"pdf", "doc", "docx", "txt", "zip", "hwp"};
}
