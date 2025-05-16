package com.example.BE_DATN.dto;


import lombok.Getter;

public class CurrentAccountDTO {
    @Getter
    private static Long idUser;

    @Getter
    private static String role;

    public static void create(Long idUser, String role) {
        CurrentAccountDTO.idUser = idUser;
        CurrentAccountDTO.role = role;
    }
}
