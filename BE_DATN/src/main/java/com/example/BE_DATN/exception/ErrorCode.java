package com.example.BE_DATN.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    ROLE_EXISTED(1,"Role existed", HttpStatus.BAD_REQUEST),
    NOT_FOUND(404,"Not found", HttpStatus.NOT_FOUND),
    IVALID_KEY(1, "Invalid message key!", HttpStatus.BAD_REQUEST),

    USER_EXISTED(2, "User existed", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER(404,"phone number empty",HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(2,"User not found", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(2,"Email existed",HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(2,"Phone must be {min} number and {max}",HttpStatus.FORBIDDEN),
    PHONE_NUMBER_EXISTED(2,"Phone number existed",HttpStatus.BAD_REQUEST),
    NAME_INVALID(2,"Name must be {min} characters or more", HttpStatus.FORBIDDEN),
    PASSWORD_INVALID(2,"Password have {min} character to {max} character ",HttpStatus.FORBIDDEN),
    NOT_FOUND_FIELD_7(404,"Field 7 not found" , HttpStatus.NOT_FOUND),
    NOT_FOUND_FIELD_11(404,"Field 11 not found" , HttpStatus.NOT_FOUND),
    NAME_EXISTED(404,"Name existed", HttpStatus.BAD_REQUEST),
    MAXIMUN_FIELD11_REACHED(404, "ID_FIELD_11 has reached the maximum of 4 entries", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
