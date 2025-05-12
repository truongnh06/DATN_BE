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
@Entity
@Table(name = "SellOff")
public class SellOff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idSellOff;
    int quantity;
    Long idReportFacility;
    Long idUser;
    LocalDate day;
    double price;
}
