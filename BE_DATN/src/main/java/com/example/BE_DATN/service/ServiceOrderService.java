package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.ServiceOrderRequest;
import com.example.BE_DATN.dto.request.ServiceOrderUpdate;
import com.example.BE_DATN.dto.respone.ServiceOrderDtoRespone;
import com.example.BE_DATN.dto.respone.ServiceOrderRespone;
import com.example.BE_DATN.entity.ServiceOrder;

import java.util.List;
import java.util.Map;

public interface ServiceOrderService {
    public ServiceOrder createServiceOrder(ServiceOrderRequest serviceOrderRequest);
    public List<ServiceOrderRespone> findServicesByIdBooking(Long idBooking);
    public List<ServiceOrder> findByIdBooking(Long idBooking);
    public List<ServiceOrderDtoRespone> getServiceOrderByIdTypeAndIdStadium(Long idType, Long idStadium);
    public void deleteServiceOrder(Long idServiceOrder);
    public ServiceOrder updateQuantityServiceOrder(Long idServiceOrder, ServiceOrderUpdate serviceOrderUpdate);
}
