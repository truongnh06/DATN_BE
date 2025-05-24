package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.BookingRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.BookingRespone;
import com.example.BE_DATN.dto.respone.RefundRespone;
import com.example.BE_DATN.entity.Booking;
import com.example.BE_DATN.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/{idStadium}")
    public  ApiRespone<List<BookingRespone>> getBookings(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<BookingRespone>>builder()
                .code(200)
                .message("Success")
                .result(bookingService.getBookings(idStadium))
                .build();
    }

    @PutMapping("/{idBooking}/enable")
    public ApiRespone<Booking> cancelBooking(@PathVariable("idBooking") Long idBooking){
        return ApiRespone.<Booking>builder()
                .code(200)
                .message("Success")
                .result(bookingService.cancelBooking(idBooking))
                .build();
    }

    @GetMapping("/{idField}/Field")
    public ApiRespone<List<BookingRespone>> getBookingByIdField(@PathVariable("idField") Long idField){
        return ApiRespone.<List<BookingRespone>>builder()
                .code(200)
                .message("Success")
                .result(bookingService.getBookingByIdField(idField))
                .build();
    }

    @GetMapping("/{idStadium}/{idUser}")
    public ApiRespone<List<BookingRespone>> getBookingByIdStadiumAndIdUser(@PathVariable("idStadium") Long idStadium,
                                                                           @PathVariable("idUser") Long idUser){
        return ApiRespone.<List<BookingRespone>>builder()
                .code(200)
                .message("Success")
                .result(bookingService.getBookingByIdStadiumAndIdUser(idStadium, idUser))
                .build();
    }

    @GetMapping("/{idStadium}/refund")
    public ApiRespone<List<RefundRespone>> getRefundResponse(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<RefundRespone>>builder()
                .code(200)
                .message("Success")
                .result(bookingService.getRefund(idStadium))
                .build();
    }

    @PutMapping("/{idBooking}/refund")
    public ApiRespone<Booking> RefundBooking(@PathVariable("idBooking") Long idBooking){
        return ApiRespone.<Booking>builder()
                .code(200)
                .message("Success")
                .result(bookingService.RefundBooking(idBooking))
                .build();
    }
}
