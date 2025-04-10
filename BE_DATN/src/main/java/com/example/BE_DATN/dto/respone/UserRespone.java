package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRespone {
    Long idUser;
    String name;
    String phoneNumber;
    String password;
    String email;
    String nameRole;
}
