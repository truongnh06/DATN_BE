package com.example.BE_DATN.exception;

import com.example.BE_DATN.dto.respone.ApiRespone;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiRespone>handlingAppException(AppException appException){
        ErrorCode errorCode = appException.getErrorCode();
        ApiRespone apiRespone = new ApiRespone();
        apiRespone.setCode(errorCode.getCode());
        apiRespone.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiRespone);
    }

    @ExceptionHandler(value = JwtException.class)
    ResponseEntity<ApiRespone> handleJwtException(JwtException ex){
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiRespone.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiRespone> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiRespone.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
        ResponseEntity<ApiRespone> handlingValidation(@NotNull MethodArgumentNotValidException exception){
        String enumKey =exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.IVALID_KEY;
        Map<String, Object> attributes = null;
        try{
            errorCode =ErrorCode.valueOf(enumKey);
            var constrainViolation =
                    exception.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);
            attributes = constrainViolation.getConstraintDescriptor().getAttributes();
        } catch (IllegalArgumentException e){

        }
        ApiRespone apiRespone = new ApiRespone();
        apiRespone.setMessage(Objects.nonNull(attributes)? mappAttribute(errorCode.getMessage(),attributes)
                : errorCode.getMessage());
        apiRespone.setCode(errorCode.getCode());
        return ResponseEntity.badRequest().body(apiRespone);
        }
    private String mappAttribute(String message, Map<String,Object> attributes){
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue)
                .replace("{" + MAX_ATTRIBUTE + "}", maxValue);
    }
}
