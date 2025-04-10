package com.example.BE_DATN.configuration;

import io.minio.MinioClient;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MinioConfig {
    @Value("${minio.url}")
    String minioUrl;

    @Value("${minio.accessKey}")
    String accessKey;

    @Value("${minio.secretKey}")
    String secretKey;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey,secretKey)
                .build();
    }
}
