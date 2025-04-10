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
@Table(name = "Field")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idField;
    Long idStadium;
    String name;
    String img;
    Long idType;
    String status;
    String enable;
}
