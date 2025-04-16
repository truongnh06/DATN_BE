package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.ServiceOrderRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.entity.ServiceOrder;
import com.example.BE_DATN.service.ServiceOrderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serviceOrder")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceOrderController {
    @Autowired
    ServiceOrderService serviceOrderService;
    @PostMapping
    public ApiRespone<ServiceOrder> createServiceOrder(@ModelAttribute ServiceOrderRequest request){
        return ApiRespone.<ServiceOrder>builder()
                .code(200)
                .message("Success")
                .result(serviceOrderService.createServiceOrder(request))
                .build();
    }
}
