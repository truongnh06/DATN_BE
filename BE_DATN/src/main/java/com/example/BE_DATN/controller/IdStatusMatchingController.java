package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.entity.IdStatusMatching;
import com.example.BE_DATN.service.IdStatusMatchingService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/idStatusMatching")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdStatusMatchingController {
    @Autowired
    IdStatusMatchingService idStatusMatchingService;

    @PostMapping
    public ApiRespone<IdStatusMatching> create(@RequestParam String name){
        return ApiRespone.<IdStatusMatching>builder()
                .code(200)
                .message("Success")
                .result(idStatusMatchingService.create(name))
                .build();
    }
}
