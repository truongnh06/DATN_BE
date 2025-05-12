package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.request.ReportFacilityRequest;
import com.example.BE_DATN.dto.respone.ReportFacilityRespone;
import com.example.BE_DATN.entity.Facility;
import com.example.BE_DATN.entity.ReportFacility;
import com.example.BE_DATN.entity.StatusFacility;
import com.example.BE_DATN.enums.Enable;
import com.example.BE_DATN.enums.StatusFacilityEnums;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.*;
import com.example.BE_DATN.service.ReportFacilityService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportFacilityServiceImpl implements ReportFacilityService {
    @Autowired
    FacilityRepository facilityRepository;
    @Autowired
    StatusFacilityRepository statusFacilityRepository;
    @Autowired
    ReportFacilityRepository reportFacilityRepository;
    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public ReportFacility createReport(ReportFacilityRequest reportFacilityRequest) {
        if(reportFacilityRequest.getQuantity() <= 0){
            throw new AppException(ErrorCode.IVALID_KEY);
        }
        if(!fieldRepository.existsFieldByIdField(reportFacilityRequest.getIdField())){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if(!userRepository.existsByIdUser(reportFacilityRequest.getIdUser())){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if(!facilityRepository.existsFieldAndFacilitySameStadium(reportFacilityRequest.getIdField()
                ,reportFacilityRequest.getIdFacility())){
            throw new AppException(ErrorCode.IVALID_KEY);
        }
        Facility facility = facilityRepository.findById(reportFacilityRequest.getIdFacility())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if(facility.getUsable() >= reportFacilityRequest.getQuantity()){
            facility.setDamaged(facility.getDamaged() + reportFacilityRequest.getQuantity());
            facility.setUsable(facility.getUsable() - reportFacilityRequest.getQuantity());
            facilityRepository.save(facility);
        } else {
            throw new AppException(ErrorCode.QUANTITY_NOT_ENOUGH);
        }
        LocalDate today = LocalDate.now();
        ReportFacility reportFacility = ReportFacility.builder()
                .idFacility(reportFacilityRequest.getIdFacility())
                .idUser(reportFacilityRequest.getIdUser())
                .note(reportFacilityRequest.getNote())
                .quantity(reportFacilityRequest.getQuantity())
                .idField(reportFacilityRequest.getIdField())
                .idStatusFacility(statusFacilityRepository
                        .getStatusByName(StatusFacilityEnums.DAMAGED.name()).map(StatusFacility::getIdStatusFacility)
                        .orElse(null))
                .day(today)
                .enable(Enable.ENABLE.name())
                .build();
        return reportFacilityRepository.save(reportFacility);
    }

    @Override
    public List<ReportFacilityRespone> getReportByIdStadium(Long idStadium) {
        return reportFacilityRepository.findReportByIdStadium(idStadium);
    }
}
