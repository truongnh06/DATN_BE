package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.Mapper.ServiceOrderMapper;
import com.example.BE_DATN.dto.request.ServiceOrderRequest;
import com.example.BE_DATN.entity.Booking;
import com.example.BE_DATN.entity.ServiceOrder;
import com.example.BE_DATN.entity.Services;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.BookingRepository;
import com.example.BE_DATN.repository.ServiceOrderRepository;
import com.example.BE_DATN.repository.ServicesRepository;
import com.example.BE_DATN.service.ServiceOrderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceOrderServiceImpl implements ServiceOrderService {
    @Autowired
    ServicesRepository servicesRepository;

    @Autowired
    ServiceOrderRepository serviceOrderRepository;

    @Autowired
    ServiceOrderMapper serviceOrderMapper;

    @Autowired
    BookingRepository bookingRepository;

    @Transactional
    @Override
    public ServiceOrder createServiceOrder(ServiceOrderRequest serviceOrderRequest) {
        Services services = servicesRepository.findById(serviceOrderRequest.getIdService())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_SERVICE));
        if(!serviceOrderRepository.existsServiceByStadium(serviceOrderRequest.getIdBooking(),
                serviceOrderRequest.getIdService())){
            throw new AppException(ErrorCode.SERVICE_INVALID);
        }
        if(services.getQuantity() < serviceOrderRequest.getQuantity()){
            throw new AppException(ErrorCode.EXCEED);
        }
        if(!bookingRepository.existsById(serviceOrderRequest.getIdBooking())){
            throw new AppException(ErrorCode.NOT_FOUND_BOOKING);
        }
        services.setQuantity(services.getQuantity() - serviceOrderRequest.getQuantity());
        services.setQuantitySold(services.getQuantitySold() + serviceOrderRequest.getQuantity());

        servicesRepository.save(services);
        ServiceOrder serviceOrder = serviceOrderMapper.toServiceOrder(serviceOrderRequest);
        serviceOrder.setTotalPrice(services.getRetailPrice() * serviceOrderRequest.getQuantity());

        return serviceOrderRepository.save(serviceOrder);
    }
}
