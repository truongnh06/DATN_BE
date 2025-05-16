package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.respone.AuthenticationRespone;
import com.example.BE_DATN.entity.Token;
import com.example.BE_DATN.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.List;

public interface AuthenticationService {
    public String buildScope(User user);
    public String generateToken(User user);
    public AuthenticationRespone LogIn(String name, String password);
    public SignedJWT verifyToken(String token) throws JOSEException, ParseException;
    public boolean introspect(String token) throws JOSEException, ParseException ;
    public Token logout(String token) throws ParseException, JOSEException;
    public SignedJWT verifyRefreshToken(String token) throws JOSEException, ParseException;
    public AuthenticationRespone refreshToken(String token) throws ParseException, JOSEException;
    public Long extractUserId(String token) throws JOSEException, ParseException;
    public String extractRole(String token) throws ParseException;
}
