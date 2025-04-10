package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PriceRespone {
    Long idPrice;
    String nameField;
    LocalTime time;
    double price;
}
