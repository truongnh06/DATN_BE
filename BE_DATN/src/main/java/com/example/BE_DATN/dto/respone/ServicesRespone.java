package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServicesRespone {
    Long idService;
    String nameStadium;
    String name;
    double costPrice;
    double retailPrice;
    int quantity;
    String enable;
    int quantitySold;
    String img;
    String unit;
}
