package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.TimeRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.entity.Time;
import com.example.BE_DATN.service.TimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/time")
public class TimeController {
    @Autowired
    TimeService timeService;
    @PostMapping
    ApiRespone<Time> createTime(@ModelAttribute TimeRequest timeRequest){
        return ApiRespone.<Time>builder()
                .message("Success")
                .code(200)
                .result(timeService.createTime(timeRequest))
                .build();
    }
    @GetMapping
    ApiRespone<List<Time>> getTime(){
        return ApiRespone.<List<Time>>builder()
                .code(200)
                .result(timeService.getTime())
                .build();
    }
}
