package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.TypeRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.entity.Type;
import com.example.BE_DATN.service.TypeService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeController {
    @Autowired
    TypeService typeService;

    @PostMapping
    ApiRespone<Type> createType(@ModelAttribute TypeRequest typeRequest){
        return ApiRespone.<Type>builder()
                .message("Success")
                .code(200)
                .result(typeService.createType(typeRequest))
                .build();
    }
    @GetMapping
    ApiRespone<List<Type>> getTypes(){
        return ApiRespone.<List<Type>>builder()
                .result(typeService.getTypes())
                .build();
    }
}
