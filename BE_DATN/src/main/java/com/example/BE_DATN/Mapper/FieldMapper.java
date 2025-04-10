package com.example.BE_DATN.Mapper;

import com.example.BE_DATN.dto.respone.FieldRespone;
import com.example.BE_DATN.entity.Field;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    FieldRespone toFieldRespone(Field field);
}
