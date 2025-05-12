package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReportRepairRespone {
    Long idReportRepair;
    String nameFacility;
    LocalDate day;
    String nameField;
    int quantity;
    double price;
    LocalDate dayRepair;
    String nameUser;
    String phoneNumber;
    String note;
    Long idFacility;
}
