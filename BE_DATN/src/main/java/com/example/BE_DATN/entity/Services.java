package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Services")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idService;
    Long idStadium;
    String name;
    double costPrice;
    double retailPrice;
    int quantity;
    String enable;
    String img;
    String unit;
    @Builder.Default
    int quantitySold = 0;
}
