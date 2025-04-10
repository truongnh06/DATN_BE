package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.PriceRequest;
import com.example.BE_DATN.dto.request.PriceUpdate;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.PriceRespone;
import com.example.BE_DATN.entity.Price;
import com.example.BE_DATN.service.PriceService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/price")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceController {
    @Autowired
    PriceService priceService;
    @PostMapping
    ApiRespone<PriceRespone> createPrice(@ModelAttribute PriceRequest request){
        return ApiRespone.<PriceRespone>builder()
                .code(200)
                .result(priceService.createPrice(request))
                .build();
    }
    @GetMapping("/{idField}")
    ApiRespone<List<Price>> getPriceField(@PathVariable("idField") Long idField){
        return ApiRespone.<List<Price>>builder()
                .code(200)
                .result(priceService.getPrceByField(idField))
                .build();
    }

    @PutMapping("/{idPrice}")
    ApiRespone<PriceRespone> updateRespone(@PathVariable("idPrice") Long idPrice, @ModelAttribute PriceUpdate priceUpdate){
        return ApiRespone.<PriceRespone>builder()
                .code(200)
                .result(priceService.updatePrice(idPrice,priceUpdate))
                .build();
    }
}
