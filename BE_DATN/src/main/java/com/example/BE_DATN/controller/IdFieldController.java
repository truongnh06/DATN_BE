package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.IdFieldRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.entity.IdField;
import com.example.BE_DATN.service.IdFieldService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/idField")
public class IdFieldController {
    @Autowired
    IdFieldService idFieldService;
    @PostMapping
    public ApiRespone<IdField> createIdField(@ModelAttribute IdFieldRequest idFieldRequest){
        return ApiRespone.<IdField>builder()
                .code(200)
                .message("Success")
                .result(idFieldService.createIdField(idFieldRequest))
                .build();
    }
}
