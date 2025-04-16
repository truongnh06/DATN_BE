package com.example.BE_DATN.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookingRequest {
    Long idUser;
    Long idPrice;
    LocalDate day;
    double totalPrice;
    String paymentStatus;
}
