package com.example.BE_DATN.service;


import com.example.BE_DATN.dto.request.BookingRequest;
import com.example.BE_DATN.dto.respone.BookingRespone;
import com.example.BE_DATN.entity.Booking;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface BookingService {
    public String createVnpayPayment(BookingRequest bookingRequest, HttpServletRequest request);
    public Booking handleVnpayReturn(Map<String,String> params);
    public List<BookingRespone> getBookings(Long idStadium);
    public Booking cancelBooking(Long idBooking);
    public List<BookingRespone> getBookingByIdField(Long idField);
}
