package com.example.BE_DATN.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FacilityRequest {
    String name;
    Long idStadium;
    int inventory;
    double price;
    MultipartFile img;
}
