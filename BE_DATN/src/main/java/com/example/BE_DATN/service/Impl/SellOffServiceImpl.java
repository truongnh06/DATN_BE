package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.request.SellOffRequest;
import com.example.BE_DATN.dto.respone.SellOffMonthlyRespone;
import com.example.BE_DATN.dto.respone.SellOffRespone;
import com.example.BE_DATN.entity.Facility;
import com.example.BE_DATN.entity.ReportFacility;
import com.example.BE_DATN.entity.SellOff;
import com.example.BE_DATN.enums.Enable;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.FacilityRepository;
import com.example.BE_DATN.repository.ReportFacilityRepository;
import com.example.BE_DATN.repository.SellOffRepository;
import com.example.BE_DATN.repository.UserRepository;
import com.example.BE_DATN.service.SellOffService;
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
public class SellOffServiceImpl implements SellOffService {
    @Autowired
    SellOffRepository sellOffRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReportFacilityRepository reportFacilityRepository;
    @Autowired
    FacilityRepository facilityRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public SellOff createSellOff(SellOffRequest request) {
        if(!userRepository.existsByIdUser(request.getIdUser())
                && !reportFacilityRepository.existsByIdReportFacility(request.getIdReportFacility())){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if(request.getQuantity() <= 0){
            throw new AppException(ErrorCode.IVALID_KEY);
        }
        ReportFacility reportFacility = reportFacilityRepository.findById(request.getIdReportFacility())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if(request.getQuantity() > reportFacility.getQuantity()){
            throw new AppException(ErrorCode.QUANTITY_NOT_ENOUGH);
        }
        if(request.getQuantity() == reportFacility.getQuantity()){
            reportFacility.setEnable(Enable.UNENABLE.name());
            reportFacility.setQuantity(0);
        } else {
            reportFacility.setQuantity(reportFacility.getQuantity() - request.getQuantity());
        }
        reportFacilityRepository.save(reportFacility);

        Facility facility = facilityRepository.findById(reportFacility.getIdFacility())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        facility.setDamaged(facility.getDamaged() - request.getQuantity());
        facilityRepository.save(facility);

        SellOff sellOff = SellOff.builder()
                .day(LocalDate.now())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .idReportFacility(request.getIdReportFacility())
                .idUser(request.getIdUser())
                .build();
        return sellOffRepository.save(sellOff);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<SellOffRespone> getListSellOff(Long idStadium) {
        return sellOffRepository.getListSellOff(idStadium);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<SellOffMonthlyRespone> getSellOffMonthly(Long idStadium) {
        List<Object[]> list = sellOffRepository.getSellOffMonthly(idStadium);
        List<SellOffMonthlyRespone> sellOffMonthlyRespones = new ArrayList<>();
        for(Object[] row : list){
            int month = ((Number) row[0]).intValue();
            int year = ((Number) row[1]).intValue();
            double total = ((Number) row[2]).doubleValue();

            sellOffMonthlyRespones.add(new SellOffMonthlyRespone(month, year, total));
        }
        return sellOffMonthlyRespones;
    }
}
