package com.example.BE_DATN.Mapper;

import com.example.BE_DATN.dto.request.ServiceOrderRequest;
import com.example.BE_DATN.entity.ServiceOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceOrderMapper {
    ServiceOrder toServiceOrder(ServiceOrderRequest serviceOrderRequest);
}
