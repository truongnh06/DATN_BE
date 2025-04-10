package com.example.BE_DATN.Mapper;

import com.example.BE_DATN.dto.request.StadiumUpdate;
import com.example.BE_DATN.entity.Stadium;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StadiumMapper {
    void updateStadium(@MappingTarget Stadium stadium, StadiumUpdate stadiumUpdate);
}
