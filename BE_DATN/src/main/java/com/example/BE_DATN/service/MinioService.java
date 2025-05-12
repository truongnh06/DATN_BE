package com.example.BE_DATN.service;

import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class MinioService {

    @Value("${minio.bucketName}")
    String bucketName;

    @Value("${minio.bucketName2}")
    String bucketName2;

    @Value("${minio.bucketName3}")
    String bucketName3;

    @Value("${minio.bucketName4}")
    String bucketName4;

    @Autowired
    MinioClient minioClient;

    @Value("${minio.url}")
    private String minioUrl;

    @PostConstruct
    public void initPublicBucket() throws Exception {
        createBucketIfNotExists(bucketName);
        createBucketIfNotExists(bucketName2);
        createBucketIfNotExists(bucketName3);
        createBucketIfNotExists(bucketName4);

        setPublicPolicy(bucketName);
        setPublicPolicy(bucketName2);
        setPublicPolicy(bucketName3);
        setPublicPolicy(bucketName4);
    }

    private void createBucketIfNotExists(String bucket) throws Exception{
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }
    private  void setPublicPolicy(String bucket) throws Exception{
        String policyJson = String.format(
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
                bucket
        );

        minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder()
                        .bucket(bucket)
                        .config(policyJson)
                        .build()
        );
    }

    public String uploadFile(MultipartFile file, String bucket, String prefix){
        if(file == null || file.isEmpty()){
            return null;
        }
        try{
            String filename = prefix + "_" + file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(filename)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return String.format("%s/%s/%s", minioUrl, bucket, filename);
        } catch (Exception e){
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
    }
}
