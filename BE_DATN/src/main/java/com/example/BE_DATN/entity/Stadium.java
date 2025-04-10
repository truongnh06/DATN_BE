package com.example.BE_DATN.entity;

import com.example.BE_DATN.enums.StadiumStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Stadium")
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idStadium;

    @Column(nullable = false, unique = true,length = 50)
    String name;
    @Column(nullable = false,unique = false,length = 100)
    String address;
    @Column(nullable = false,unique = false,length = 20)
    String phoneNumber;
    @Column(nullable = true,length = 255)
    String img;
    @Column(nullable = false, length = 20)
    String status;
    @Column(nullable = false)
    String enable;
}
