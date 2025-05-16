package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.AuthenticationRespone;
import com.example.BE_DATN.entity.Token;
import com.example.BE_DATN.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@Slf4j
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiRespone<AuthenticationRespone> login(@RequestParam("name") String name,
                                                   @RequestParam("password") String password){
        return ApiRespone.<AuthenticationRespone>builder()
                .code(200)
                .message("Success")
                .result(authenticationService.LogIn(name, password))
                .build();
    }

    @PostMapping("/logout")
    public ApiRespone<Token> logout(@RequestParam("token") String token) throws ParseException, JOSEException {
        return ApiRespone.<Token>builder()
                .code(200)
                .message("Success")
                .result(authenticationService.logout(token))
                .build();
    }

    @PostMapping("/refresh")
    public ApiRespone<AuthenticationRespone> refreshToken(@RequestParam("token") String token)
            throws ParseException, JOSEException {
        return ApiRespone.<AuthenticationRespone>builder()
                .code(200)
                .message("Success")
                .result(authenticationService.refreshToken(token))
                .build();
    }
}
