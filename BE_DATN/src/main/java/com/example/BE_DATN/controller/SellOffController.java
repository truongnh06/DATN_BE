package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.SellOffRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.SellOffMonthlyRespone;
import com.example.BE_DATN.dto.respone.SellOffRespone;
import com.example.BE_DATN.entity.SellOff;
import com.example.BE_DATN.service.SellOffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/selloff")
@Slf4j
public class SellOffController {
    @Autowired
    SellOffService sellOffService;

    @PostMapping
    public ApiRespone<SellOff> createSellOff(@ModelAttribute SellOffRequest request){
        return ApiRespone.<SellOff>builder()
                .code(200)
                .message("Success")
                .result(sellOffService.createSellOff(request))
                .build();
    }

    @GetMapping("/{idStadium}")
    public ApiRespone<List<SellOffRespone>> getListSellOff(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<SellOffRespone>>builder()
                .code(200)
                .message("Success")
                .result(sellOffService.getListSellOff(idStadium))
                .build();
    }

    @GetMapping("/{idStadium}/monthly")
    public ApiRespone<List<SellOffMonthlyRespone>> getSellOffMonthly(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<SellOffMonthlyRespone>>builder()
                .code(200)
                .message("Success")
                .result(sellOffService.getSellOffMonthly(idStadium))
                .build();
    }
}
