package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.configuration.VNPayConfig;
import com.example.BE_DATN.dto.request.BookingRequest;
import com.example.BE_DATN.dto.respone.BookingRespone;
import com.example.BE_DATN.entity.Booking;
import com.example.BE_DATN.entity.Price;
import com.example.BE_DATN.enums.PaymentStatus;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.BookingRepository;
import com.example.BE_DATN.repository.FieldRepository;
import com.example.BE_DATN.repository.PriceRepository;
import com.example.BE_DATN.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    VNPayConfig vnPayConfig;

    @Override
    public String createVnpayPayment(BookingRequest bookingRequest, HttpServletRequest request) {
        if(bookingRequest.getDay() == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
    String status = fieldRepository.getStatusByIdPrice(bookingRequest.getIdPrice());
        if("INACTIVE".equalsIgnoreCase(status)){
            throw new AppException(ErrorCode.FIELD_NOT_OPERATION);
        }
        Booking booking = Booking.builder()
                .idUser(bookingRequest.getIdUser())
                .idPrice(bookingRequest.getIdPrice())
                .day(bookingRequest.getDay())
                .totalPrice(bookingRequest.getTotalPrice())
                .paymentStatus(PaymentStatus.UNPAID.name())
                .build();
        Booking saveBooking = bookingRepository.save(booking);
        String vnp_TxnRef = String.valueOf(saveBooking.getIdBooking());
        String vnp_OrderInfo = "Payment for football field booking: " + vnp_TxnRef;
        String vnp_Amount = String.valueOf((long) (saveBooking.getTotalPrice() * 100));
        String vnp_IpAddr = request.getRemoteAddr();
        String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnPayConfig.getVersion());
        vnp_Params.put("vnp_Command", vnPayConfig.getCommand());
        vnp_Params.put("vnp_TmnCode", vnPayConfig.getTmnCode());
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String value = vnp_Params.get(fieldName);
            if (value != null && !value.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
                query.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
            }
        }

        String rawHash = hashData.substring(0, hashData.length() - 1);
        String queryUrl = query.substring(0, query.length() - 1);

        String vnp_SecureHash = hmacSHA512(vnPayConfig.getHashSecret(), rawHash);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        return vnPayConfig.getPayUrl() + "?" + queryUrl;
    }

    @Override
    public Booking handleVnpayReturn(Map<String, String> params) {
        String responseCode = params.get("vnp_ResponseCode");
        String txnRef = params.get("vnp_TxnRef");

        Optional<Booking> bookingOptional = bookingRepository.findById(Long.valueOf(txnRef));
        if (bookingOptional.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }

        Booking booking = bookingOptional.get();
        if ("00".equals(responseCode)) {
            booking.setPaymentStatus(PaymentStatus.PAID.name());
        } else {
            booking.setPaymentStatus(PaymentStatus.UNPAID.name());
        }

        bookingRepository.save(booking);
        return bookingRepository.save(booking);
    }

    @Override
    public List<BookingRespone> getBookings() {
        return bookingRepository.findAllBooking();
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] bytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hash.append('0');
                hash.append(hex);
            }
            return hash.toString();
        } catch (Exception e) {
            throw new AppException(ErrorCode.IVALID_KEY);
        }
    }
}
