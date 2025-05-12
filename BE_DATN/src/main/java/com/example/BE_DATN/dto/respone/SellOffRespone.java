package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SellOffRespone {
    String nameFacility;
    LocalDate day;
    String nameField;
    int quantity;
    double price;
    String nameUser;
    String phoneNumber;
    String note;
    String status;
}
