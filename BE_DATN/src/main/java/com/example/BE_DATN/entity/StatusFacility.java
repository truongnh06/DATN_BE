package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "StatusFacility")
public class StatusFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idStatusFacility;
    String name;
}
