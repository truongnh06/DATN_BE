package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.respone.BillServiceMonthlyRespone;
import com.example.BE_DATN.dto.respone.BillServiceRespone;
import com.example.BE_DATN.entity.BillService;
import com.example.BE_DATN.entity.Services;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.BillServiceRepository;
import com.example.BE_DATN.repository.ServicesRepository;
import com.example.BE_DATN.service.BillServiceService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillServiceServiceImpl implements BillServiceService {
    @Autowired
    ServicesRepository servicesRepository;
    @Autowired
    BillServiceRepository billServiceRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public BillService createBillService(Long idService, double costPrice, int quantity) {
        Services services = servicesRepository.findServiceByID(idService)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICES_NOT_FOUND));

        BillService billService = BillService.builder()
                .idService(idService)
                .costPrice(costPrice)
                .total(costPrice * quantity)
                .quantity(quantity)
                .day(LocalDate.now())
                .build();
        BillService saveBillService = billServiceRepository.save(billService);

        services.setQuantity(services.getQuantity() + saveBillService.getQuantity());
        services.setCostPrice(saveBillService.getCostPrice());
        servicesRepository.save(services);
        return saveBillService;
    }

    @Override
    public List<BillServiceRespone> getListBillService(Long idService) {
        return billServiceRepository.getListBillService(idService);
    }

    @Override
    public List<BillServiceMonthlyRespone> getServiceMonthlyByIdStadium(Long idStadium) {
        List<Object[]> list = billServiceRepository.getServiceMonthlyByIdStadium(idStadium);
        List<BillServiceMonthlyRespone> billServiceMonthlyRespones = new ArrayList<>();
        for (Object[] row : list){
            int month = ((Number) row[0]).intValue();
            int year = ((Number) row[1]).intValue();
            double total = ((Number) row[2]).doubleValue();
            billServiceMonthlyRespones.add(new BillServiceMonthlyRespone(month,year,total));
        }
        return billServiceMonthlyRespones;
    }
}
