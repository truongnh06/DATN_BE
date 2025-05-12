package com.example.BE_DATN.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserRequest {
    @NotBlank
    @Size(min = 5, message = "NAME_INVALID")
    String name;

    @NotBlank
    @Size(min = 8, max = 10, message = "PASSWORD_INVALID")
    String password;

    @NotBlank
    @Size(min = 10, max = 20, message = "PHONE_NUMBER_INVALID")
    String phoneNumber;

    @NotBlank
    String email;
}
