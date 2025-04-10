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
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUser;

    @Column(nullable = false)
    Long idRole;

    @Column(nullable = false, unique = true, length = 50)
    String name;

    @Column(name = "phoneNumber",nullable = false,unique = false,length = 20)
    String phoneNumber;

    @Column(nullable = false,unique = false,length = 10)
    String password;

    @Column(nullable = false,unique = false,length = 50)
    String email;

}
