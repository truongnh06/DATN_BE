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
    @Table(name = "Facility")
    public class Facility {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long idFacility;
        Long idStadium;
        String img;
        String name;
        int usable;
        int inventory;
        @Builder.Default
        double price = 0;
        String enable;
        @Builder.Default
        int damaged = 0;
    }
