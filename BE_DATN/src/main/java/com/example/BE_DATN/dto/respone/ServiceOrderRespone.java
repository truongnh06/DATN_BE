package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ServiceOrderRespone {
    Long idServiceOrder;
    Long idBooking;
    String nameService;
    int quantity;
    double totalPrice;
    double retailPrice;
}
