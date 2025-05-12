package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ReportFacility")
@Builder
public class ReportFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idReportFacility;
    Long idFacility;
    Long idUser;
    Long idField;
    int quantity;
    Long idStatusFacility;
    LocalDate day;
    String enable;
    String note;
}
