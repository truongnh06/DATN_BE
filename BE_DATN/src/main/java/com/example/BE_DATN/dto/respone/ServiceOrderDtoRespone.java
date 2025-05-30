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
public class ServiceOrderDtoRespone {
    Long idServiceOrder;
    Long idBooking;
    Long idService;
    String nameField;
    String nameService;
    int quantity;
    LocalTime time;
    LocalDate day;
    double totalPrice;
}
