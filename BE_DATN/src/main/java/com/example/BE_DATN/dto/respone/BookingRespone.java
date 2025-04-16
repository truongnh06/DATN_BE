package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookingRespone {
    Long idBooking;
    String nameUser;
    String phoneNumber;
    String nameField;
    String nameStadium;
    double price;
    LocalTime time;
    LocalDate day;
    double totalPrice;
    String paymentStatus;
}
