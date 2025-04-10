package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "IdField")
@Builder
public class IdField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idIdField;
    Long idField11;
    Long idField7;
}
