package com.example.BE_DATN.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StadiumUpdate {
    String name;
    String address;
    String phoneNumber;
    String enable;
    String status;
}
