package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.BookingRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.BookingRespone;
import com.example.BE_DATN.entity.Booking;
import com.example.BE_DATN.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
@Slf4j
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/create-payment")
    public ApiRespone<String> createPayment(@ModelAttribute BookingRequest bookingRequest, HttpServletRequest request) {
        return ApiRespone.<String>builder()
                .code(200)
                .message("Success")
                .result(bookingService.createVnpayPayment(bookingRequest,request))
                .build();
    }

    @PostMapping("/vnpay-return")
    public ApiRespone<Booking> vnpayReturn(@RequestBody Map<String, String> params) {
        return ApiRespone.<Booking>builder()
                .code(200)
                .message("Thanh toán thành công")
                .result(bookingService.handleVnpayReturn(params))
                .build();
    }

    @GetMapping
    public  ApiRespone<List<BookingRespone>> getBookings(){
        return ApiRespone.<List<BookingRespone>>builder()
                .code(200)
                .message("Success")
                .result(bookingService.getBookings())
                .build();
    }
}
