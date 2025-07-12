package com.ftm.main.filemanager.fileOperations.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Service
public class S3PresignedUrlService {

    @Value("${aws.s3.bucket}")
    private String bucket;

    private final S3Presigner s3Presigner;

    public S3PresignedUrlService(S3Presigner s3Presigner) {
        this.s3Presigner = s3Presigner;
    }

    public URL generatePutUrl(String key, Duration duration){
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        PutObjectPresignRequest preSignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(duration)
                .putObjectRequest(putRequest)
                .build();
        return s3Presigner.presignPutObject(preSignRequest).url();
    }

    public URL generateGetUrl(String key, Duration duration){
        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        GetObjectPresignRequest preSignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(duration)
                .getObjectRequest(getRequest)
                .build();
        return s3Presigner.presignGetObject(preSignRequest).url();
    }
}
