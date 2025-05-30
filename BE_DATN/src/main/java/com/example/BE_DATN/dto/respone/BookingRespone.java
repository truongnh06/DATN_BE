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
    String nameField;
    String nameType;
    LocalTime time;
    LocalDate day;
    String paymentStatus;
    String nameStadium;
    String address;
    String phoneNumberStadium;
    String nameUser;
    String phoneNumberUser;
    double totalPrice;
}
