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
@Table(name = "IdStatusMatching")
public class IdStatusMatching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idStatusMatching;
    String name;
}
