package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.request.BillFacilityRequest;
import com.example.BE_DATN.dto.respone.BillFacilityRespone;
import com.example.BE_DATN.dto.respone.FacilityMonthlyRespone;
import com.example.BE_DATN.entity.BillFacility;
import com.example.BE_DATN.entity.Facility;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.BillFacilityRepository;
import com.example.BE_DATN.repository.FacilityRepository;
import com.example.BE_DATN.service.BillFacilityService;
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
public class BillFacilityServiceImpl implements BillFacilityService {
    @Autowired
    BillFacilityRepository billFacilityRepository;
    @Autowired
    FacilityRepository facilityRepository;

    @Override
    public List<BillFacilityRespone> getBillFacilityByIdFacility(Long idFacility) {
        return billFacilityRepository.getBillFacilityByIdFacility(idFacility);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public BillFacility createBillFacility(BillFacilityRequest request) {
        Facility facility = facilityRepository.findById(request.getIdFacility())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        BillFacility billFacility = BillFacility.builder()
                .idFacility(request.getIdFacility())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .totalPrice(request.getPrice() * request.getQuantity())
                .day(LocalDate.now())
                .build();

        facility.setInventory(facility.getInventory() + request.getQuantity());
        facilityRepository.save(facility);
        return billFacilityRepository.save(billFacility);
    }

    @Override
    public List<FacilityMonthlyRespone> getFacilityMonthly(Long idStadium) {
        List<Object[]> list = billFacilityRepository.getFacilityMonthly(idStadium);
        List<FacilityMonthlyRespone> facilityMonthlyRespones = new ArrayList<>();
        for(Object[] row : list){
            int month = ((Number) row[0]).intValue();
            int year = ((Number) row[1]).intValue();
            double total = ((Number) row[2]).doubleValue();
            facilityMonthlyRespones.add(new FacilityMonthlyRespone(month,year,total));
        }
        return facilityMonthlyRespones;
    }
}
