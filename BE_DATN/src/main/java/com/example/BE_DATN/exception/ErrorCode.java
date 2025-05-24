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
    EMAIL_NOT_FOUND(404,"Email not found",HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(2,"Phone must be {min} number and {max}",HttpStatus.FORBIDDEN),
    PHONE_NUMBER_EXISTED(2,"Phone number existed",HttpStatus.BAD_REQUEST),
    NAME_INVALID(2,"Name must be {min} characters or more", HttpStatus.FORBIDDEN),
    PASSWORD_INVALID(2,"Password have {min} character to {max} character ",HttpStatus.FORBIDDEN),

    FIELD7_EXISTED(404, "Field 7 existed", HttpStatus.BAD_REQUEST),
    NOT_FOUND_FIELD_7(404,"Field 7 not found" , HttpStatus.NOT_FOUND),
    NOT_FOUND_FIELD_11(404,"Field 11 not found" , HttpStatus.NOT_FOUND),
    FIELD_NOT_OPERATION(404, "The field is currently not in operation", HttpStatus.BAD_REQUEST),

    NAME_EXISTED(404,"Name existed", HttpStatus.BAD_REQUEST),
    QUANTITY_REMAIN(404, "Quantity is still available", HttpStatus.BAD_REQUEST),
    MAXIMUN_FIELD11_REACHED(404, "ID_FIELD_11 has reached the maximum of 4 entries", HttpStatus.BAD_REQUEST),

    EXCEED(404, "Not enough quantity in stock for service", HttpStatus.BAD_REQUEST),
    NOT_FOUND_SERVICE(404, "Not found ID service", HttpStatus.NOT_FOUND),
    NOT_FOUND_BOOKING(404, "Not found ID booking", HttpStatus.NOT_FOUND),
    BOOKING_NOT_PAY(404, "Booking has not been paid yet", HttpStatus.BAD_REQUEST),
    CANNOT_CANCEL_BOOKING(404, "Not cancel booking", HttpStatus.BAD_REQUEST),
    CANNOT_CANCEL_BOOKING_MATCH(404, "Booking cancellation failed: the field has already been matched and cannot be canceled.", HttpStatus.BAD_REQUEST),
    BOOKING_DAY_INVALID(404, "Day invalid!", HttpStatus.BAD_REQUEST),
    BOOKING_INVALID(404, "Booking Invalid!", HttpStatus.BAD_REQUEST),
    BOOKING_EXISTS(404, "Booking exists!", HttpStatus.BAD_REQUEST),

    CONFLICT_SCHEDULE(404, "Schedule conflict" , HttpStatus.BAD_REQUEST),
    MATCH_NOT_FOUND(404, "Match not found", HttpStatus.BAD_REQUEST),

    SERVICES_NOT_FOUND(404, "Services not found", HttpStatus.BAD_REQUEST),

    UNAUTHORIZED(403, "You do not have permission", HttpStatus.FORBIDDEN),
    UNCATEGORIZED_EXCEPTION(401, "Unauthenticated!", HttpStatus.UNAUTHORIZED),

    SERVICE_INVALID(404, "Service invalid!", HttpStatus.BAD_REQUEST),
    QUANTITY_NOT_ENOUGH(404, "Quantity is not enough", HttpStatus.BAD_REQUEST),



    MATCHING_EXISTS(404, "Matching exists!" , HttpStatus.BAD_REQUEST)
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
