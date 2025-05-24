package com.example.BE_DATN.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StadiumRequest {
    @NotBlank
    @Size(min = 5, message = "STADIUM_INVALID")
    String name;
    @NotBlank
    String address;
    @NotBlank(message = "PHONE_NUMBER")
    @Size(min = 10 , max = 20 , message = "PHONE_NUMBER_INVALID")
    String phoneNumber;
    Long idDistrict;
    MultipartFile img;
}
