package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "District")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idDistrict;
    String name;
}
