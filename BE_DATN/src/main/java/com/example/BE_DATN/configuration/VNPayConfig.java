package com.example.BE_DATN.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class VNPayConfig {
    @Value("${vnpay.version}")
    String version;

    @Value("${vnpay.command}")
    String command;

    @Value("${vnpay.tmnCode}")
    String tmnCode;
    @Value("${vnpay.hashSecret}")
    String hashSecret;

    @Value("${vnpay.payUrl}")
    String payUrl;

    @Value("${vnpay.returnUrl}")
    String returnUrl;
}
