package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.entity.District;
import com.example.BE_DATN.service.DistrictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/district")
@Slf4j
public class DistrictController {
    @Autowired
    DistrictService districtService;
    @PostMapping
    public ApiRespone<District> createDistrict(@RequestParam("name") String name){
        return ApiRespone.<District>builder()
                .code(200)
                .message("Success")
                .result(districtService.createDistrict(name))
                .build();
    }

    @GetMapping
    public ApiRespone<List<District>> getAllDistrict(){
        return ApiRespone.<List<District>>builder()
                .code(200)
                .message("Success")
                .result(districtService.getAllDistrict())
                .build();
    }
}
