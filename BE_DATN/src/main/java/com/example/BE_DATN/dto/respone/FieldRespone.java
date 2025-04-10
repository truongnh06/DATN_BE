package com.example.BE_DATN.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldRespone {
    Long idField;
    String name;
    String nameStadium;
    String img;
    String nameType;
    String status;
    String enable;

}
