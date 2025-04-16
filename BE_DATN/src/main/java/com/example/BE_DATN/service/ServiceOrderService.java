package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.ServiceOrderRequest;
import com.example.BE_DATN.entity.ServiceOrder;

public interface ServiceOrderService {
    public ServiceOrder createServiceOrder(ServiceOrderRequest serviceOrderRequest);
}
