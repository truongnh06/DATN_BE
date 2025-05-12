package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "BillService")
public class BillService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idBillService;
    Long idService;
    double total;
    int quantity;
    LocalDate day;
    double costPrice;
}
