package com.example.BE_DATN.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServicesRequest {
    Long idStadium;
    String name;
    double costPrice;
    double retailPrice;
    int quantity;
    MultipartFile img;
    String unit;
}
