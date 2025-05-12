package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.MatchCancelRespone;
import com.example.BE_DATN.service.MatchCancelService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matchcancel")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchCancelController {
    @Autowired
    MatchCancelService matchCancelService;

    @GetMapping
    public ApiRespone<List<MatchCancelRespone>> getMatchCancel(){
        return ApiRespone.<List<MatchCancelRespone>>builder()
                .code(200)
                .message("Success")
                .result(matchCancelService.getTopUserCancel())
                .build();
    }
}
