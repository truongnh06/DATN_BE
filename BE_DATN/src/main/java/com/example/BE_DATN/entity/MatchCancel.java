package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MatchCancel")
public class MatchCancel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMatchCancel;
    Long idMatching;
    Long idUserCancel;
    String reason;
    LocalDate day;
}
