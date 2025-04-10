package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPrice;
    Long idField;
    Long idTime;
    double price;
}
