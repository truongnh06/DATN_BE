package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportFacilityRespone {
    Long idReportFacility;
    String nameFacility;
    LocalDate day;
    String nameField;
    int quantity;
    String nameStatus;
    String nameUser;
    String phoneNumber;
    String note;
}
