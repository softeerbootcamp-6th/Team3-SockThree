package com.seniclass.server.domain.aws.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;

import java.io.IOException;
import java.io.UncheckedIOException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Getter
    @Value("${aws.region}") private String region;
    @Getter
    @Value("${aws.s3.bucket}") private String bucket;

    public String upload(String key, byte[] bytes, String contentType) {
        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                // (권장) 서버사이드 암호화
                .serverSideEncryption(ServerSideEncryption.AES256)
                .build();

        s3Client.putObject(putReq, RequestBody.fromBytes(bytes));
        return key;
    }

    public byte[] download(String key) {
        GetObjectRequest getReq = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        try (ResponseInputStream<GetObjectResponse> in = s3Client.getObject(getReq)) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void delete(String key) {
        s3Client.deleteObject(b -> b.bucket(bucket).key(key));
    }

}

