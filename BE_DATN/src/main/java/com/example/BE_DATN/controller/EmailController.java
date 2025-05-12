package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@Slf4j
public class EmailController {
    @Autowired
    EmailService emailService;
    @PostMapping
    public ApiRespone<Void> senNewPassword(@RequestParam("email") String email){
        emailService.sendNewPassword(email);
        return ApiRespone.<Void>builder()
                .code(200)
                .message("Success")
                .build();
    }
}
