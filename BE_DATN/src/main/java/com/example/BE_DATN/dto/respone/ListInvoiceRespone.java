package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListInvoiceRespone {
    Long idInvoice;
    LocalDate day;
    Long idStadium;
    String name;
    Long idBooking;
    double totalPrice;
}
