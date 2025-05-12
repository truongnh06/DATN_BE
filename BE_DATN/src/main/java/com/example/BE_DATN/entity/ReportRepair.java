package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "ReportRepair")
@Entity
public class ReportRepair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idReportRepair;
    Long idReportFacility;
    Long idUser;
    double price;
    int quantity;
    LocalDate day;
    String enable;
}
