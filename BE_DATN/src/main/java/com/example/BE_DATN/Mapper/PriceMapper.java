package com.example.BE_DATN.Mapper;
import com.example.BE_DATN.dto.request.PriceRequest;
import com.example.BE_DATN.dto.respone.PriceRespone;
import com.example.BE_DATN.entity.Price;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PriceMapper {
    Price toPrice(PriceRequest request);
    PriceRespone toPriceRespone(Price price);
}
