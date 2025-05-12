package com.example.BE_DATN.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchingRequest {
    Long idUserA;
    Long idField;
    LocalDate day;
    Long idTime;
    String notes;
}
