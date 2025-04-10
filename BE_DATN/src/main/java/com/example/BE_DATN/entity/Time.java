package com.example.BE_DATN.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@Table(name = "Time")
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idTime;
    LocalTime time;
}
