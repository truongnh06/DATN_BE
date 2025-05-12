package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.entity.StatusFacility;
import com.example.BE_DATN.service.StatusFacilityService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statusFacility")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusFacilityController {
    @Autowired
    StatusFacilityService statusFacilityService;
    @PostMapping
    public ApiRespone<StatusFacility> createStatusFacility(@RequestParam String name){
        return ApiRespone.<StatusFacility>builder()
                .code(200)
                .message("Success")
                .result(statusFacilityService.createStatusFacility(name))
                .build();
    }
}
