package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.PriceRequest;
import com.example.BE_DATN.dto.request.PriceUpdate;
import com.example.BE_DATN.dto.respone.PriceRespone;
import com.example.BE_DATN.entity.Price;

import java.util.List;

public interface PriceService {
    public PriceRespone createPrice(PriceRequest request);
    public List<Price> getPrceByField(Long idField);
    public PriceRespone updatePrice(Long idPrice, PriceUpdate priceUpdate);
}
