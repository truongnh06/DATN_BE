package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "BillFacility")
public class BillFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idBillFacility;
    Long idFacility;
    int quantity;
    double price;
    double totalPrice;
    LocalDate day;
}
