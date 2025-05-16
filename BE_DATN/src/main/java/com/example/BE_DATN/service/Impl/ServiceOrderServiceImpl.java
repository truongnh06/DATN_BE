package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.Mapper.ServiceOrderMapper;
import com.example.BE_DATN.dto.CurrentAccountDTO;
import com.example.BE_DATN.dto.request.ServiceOrderRequest;
import com.example.BE_DATN.dto.request.ServiceOrderUpdate;
import com.example.BE_DATN.dto.respone.ServiceOrderDtoRespone;
import com.example.BE_DATN.dto.respone.ServiceOrderRespone;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        Booking booking = bookingRepository.findById(serviceOrderRequest.getIdBooking())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        LocalDate today = LocalDate.now();
        if(!booking.getDay().isAfter(today)){
            throw new AppException(ErrorCode.BOOKING_DAY_INVALID);
        }
        if(booking.getPaymentStatus().equalsIgnoreCase("UNPAID")){
            throw new AppException(ErrorCode.BOOKING_NOT_PAY);
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

    @Override
    public List<ServiceOrderRespone> findServicesByIdBooking(Long idBooking) {
        return serviceOrderRepository.findServicesByIdBooking(idBooking);
    }

    @Override
    public List<ServiceOrder> findByIdBooking(Long idBooking) {
        return serviceOrderRepository.findByIdBooking(idBooking);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<ServiceOrderDtoRespone> getServiceOrderByIdTypeAndIdStadium(Long idType, Long idStadium) {
        return serviceOrderRepository.findServiceOrderDtoByIdStadiumAndIdType(idType,idStadium);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public void deleteServiceOrder(Long idServiceOrder) {
        ServiceOrderDtoRespone serviceOrderDtoRespone = serviceOrderRepository.findByIdServiceOrder(idServiceOrder)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        LocalDate today = LocalDate.now();
        if(!serviceOrderDtoRespone.getDay().isAfter(today)){
            throw new AppException(ErrorCode.BOOKING_DAY_INVALID);
        }
        Services services = servicesRepository.findById(serviceOrderDtoRespone.getIdService())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        services.setQuantity(services.getQuantity() + serviceOrderDtoRespone.getQuantity());
        services.setQuantitySold(services.getQuantitySold() - serviceOrderDtoRespone.getQuantity());
        servicesRepository.save(services);
        serviceOrderRepository.deleteByIdServiceOrder(serviceOrderDtoRespone.getIdServiceOrder());
        return;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public ServiceOrder updateQuantityServiceOrder(Long idServiceOrder, ServiceOrderUpdate serviceOrderUpdate) {
        ServiceOrderDtoRespone serviceOrderDtoRespone = serviceOrderRepository.findByIdServiceOrder(idServiceOrder)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        LocalDate today = LocalDate.now();
        if(!serviceOrderDtoRespone.getDay().isAfter(today)){
            throw new AppException(ErrorCode.BOOKING_DAY_INVALID);
        }
        ServiceOrder serviceOrder = serviceOrderRepository.findById(idServiceOrder)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        Services services = servicesRepository.findById(serviceOrder.getIdService())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        int diffQuantity = serviceOrderUpdate.getQuantity() - serviceOrder.getQuantity();
        if(diffQuantity > 0 && services.getQuantity() < diffQuantity){
            throw new AppException(ErrorCode.QUANTITY_NOT_ENOUGH);
        }
        services.setQuantity(services.getQuantity() - diffQuantity);
        services.setQuantitySold(services.getQuantitySold() + diffQuantity);
        servicesRepository.save(services);

        serviceOrder.setQuantity(serviceOrderUpdate.getQuantity());
        serviceOrder.setTotalPrice(services.getRetailPrice() * serviceOrderUpdate.getQuantity());
        return serviceOrderRepository.save(serviceOrder);
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    public List<ServiceOrderDtoRespone> getServiceOrderByIdUser(Long idType, Long idStadium, Long idUser) {
        if(!Objects.equals(CurrentAccountDTO.getIdUser(),idUser)){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return serviceOrderRepository.findServiceOrderByIdUserAndIdStadiumType(idType,idStadium,idUser);
    }

}
