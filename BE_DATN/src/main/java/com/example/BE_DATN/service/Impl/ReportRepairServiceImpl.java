package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.request.ReportRepairRequest;
import com.example.BE_DATN.dto.respone.RepairMonthlyRespone;
import com.example.BE_DATN.dto.respone.ReportRepairRespone;
import com.example.BE_DATN.entity.Facility;
import com.example.BE_DATN.entity.ReportFacility;
import com.example.BE_DATN.entity.ReportRepair;
import com.example.BE_DATN.enums.Enable;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.FacilityRepository;
import com.example.BE_DATN.repository.ReportFacilityRepository;
import com.example.BE_DATN.repository.ReportRepairRepository;
import com.example.BE_DATN.repository.UserRepository;
import com.example.BE_DATN.service.ReportRepairService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportRepairServiceImpl implements ReportRepairService {
    @Autowired
    ReportRepairRepository reportRepairRepository;

    @Autowired
    ReportFacilityRepository reportFacilityRepository;

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public ReportRepair createReportRepair(ReportRepairRequest request) {
        ReportFacility reportFacility = reportFacilityRepository.findById(request.getIdReportFacility())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if(!userRepository.existsByIdUser(request.getIdUser())){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if(request.getQuantity() <= 0){
            throw new AppException(ErrorCode.IVALID_KEY);
        }
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
        facility.setUsable(facility.getUsable() + request.getQuantity());
        facility.setDamaged(facility.getDamaged() - request.getQuantity());
        facilityRepository.save(facility);

        ReportRepair reportRepair = ReportRepair.builder()
                .idReportFacility(request.getIdReportFacility())
                .quantity(request.getQuantity())
                .day(LocalDate.now())
                .idUser(request.getIdUser())
                .enable(Enable.ENABLE.name())
                .price(request.getPrice())
                .build();
        return reportRepairRepository.save(reportRepair);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<ReportRepairRespone> getAllReportRepairRespone(Long idStadium) {
        return reportRepairRepository.getReportRepairRespone(idStadium);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<RepairMonthlyRespone> getRepairMonthly(Long idStadium) {
        List<Object[]> list = reportRepairRepository.getRepairMonthly(idStadium);
        List<RepairMonthlyRespone> repairMonthlyRespones = new ArrayList<>();
        for(Object[] row : list){
            int month = ((Number) row[0]).intValue();
            int year = ((Number) row[1]).intValue();
            int total = ((Number) row[2]).intValue();
            repairMonthlyRespones.add(new RepairMonthlyRespone(month, year, total));
        }
        return repairMonthlyRespones;
    }
}
