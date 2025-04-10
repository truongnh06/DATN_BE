package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StadiumRespone {
    Long idStadium;
    String name;
    String address;
    String phoneNumber;
    String status;
    String enable;
    String img;
}
