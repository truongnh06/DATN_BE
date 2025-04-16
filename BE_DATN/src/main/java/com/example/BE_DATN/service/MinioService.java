package com.example.BE_DATN.service;

import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class MinioService {

    @Value("${minio.bucketName}")
    String bucketName;

    @Value("${minio.bucketName2}")
    String bucketName2;

    @Value(("${minio.bucketName3}"))
    String bucketName3;
    @Autowired
    MinioClient minioClient;

    @PostConstruct // Tự động chạy khi ứng dụng khởi động
    public void initPublicBucket() throws Exception {
        // Kiểm tra và tạo bucket nếu chưa tồn tại cho bucket 1
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // Kiểm tra và tạo bucket nếu chưa tồn tại cho bucket 2
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName2).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName2).build());
        }

        // Cấu hình policy public cho bucket 1
        String policyJson1 = String.format(
                """
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Effect": "Allow",
                            "Principal": "*",
                            "Action": ["s3:GetObject"],
                            "Resource": ["arn:aws:s3:::%s/*"]
                        }
                    ]
                }
                """,
                bucketName
        );

        minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder()
                        .bucket(bucketName)
                        .config(policyJson1)
                        .build()
        );

        // Cấu hình policy public cho bucket 2
        String policyJson2 = String.format(
                """
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Effect": "Allow",
                            "Principal": "*",
                            "Action": ["s3:GetObject"],
                            "Resource": ["arn:aws:s3:::%s/*"]
                        }
                    ]
                }
                """,
                bucketName2
        );

        minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder()
                        .bucket(bucketName2)
                        .config(policyJson2)
                        .build()
        );

        // Cấu hình policy public cho bucket 3
        String policyJson3 = String.format(
                """
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Effect": "Allow",
                            "Principal": "*",
                            "Action": ["s3:GetObject"],
                            "Resource": ["arn:aws:s3:::%s/*"]
                        }
                    ]
                }
                """,
                bucketName3
        );

        minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder()
                        .bucket(bucketName3)
                        .config(policyJson3)
                        .build()
        );
    }
}
