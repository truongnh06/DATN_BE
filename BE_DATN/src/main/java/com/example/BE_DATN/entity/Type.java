package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idType;
    String name;
}
