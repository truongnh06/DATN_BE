package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchingRespone {
    Long idMatching;
    String nameUserA;
    String nameUserB;
    String phoneNumberA;
    String phoneNumberB;
    String nameField;
    String nameType;
    LocalDate day;
    LocalTime time;
    String notes;
    String nameStatusMatching;
    String nameStadium;
    String address;
    double price;
    String enable;
}
