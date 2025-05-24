package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.request.FacilityRequest;
import com.example.BE_DATN.dto.request.FacilityUpdate;
import com.example.BE_DATN.entity.BillFacility;
import com.example.BE_DATN.entity.Facility;
import com.example.BE_DATN.enums.Enable;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.BillFacilityRepository;
import com.example.BE_DATN.repository.FacilityRepository;
import com.example.BE_DATN.repository.StadiumRepository;
import com.example.BE_DATN.service.FacilityService;
import com.example.BE_DATN.service.MinioService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FacilityServiceImpl implements FacilityService {
    @Autowired
    FacilityRepository facilityRepository;
    @Autowired
    MinioService minioService;
    @Value("${minio.bucketName4}")
    String bucketName;
    @Autowired
    StadiumRepository stadiumRepository;

    @Autowired
    BillFacilityRepository billFacilityRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public Facility createFacility(FacilityRequest facilityRequest, MultipartFile file) {
        String imgUrl = minioService.uploadFile(file,bucketName,"Facility");
        if(facilityRepository.existsByName(facilityRequest.getName(), facilityRequest.getIdStadium())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }
        if(!stadiumRepository.existsByIdStadium(facilityRequest.getIdStadium())){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if(facilityRequest.getInventory() == 0){
            throw new AppException(ErrorCode.IVALID_KEY);
        }
        Facility facility = Facility.builder()
                .idStadium(facilityRequest.getIdStadium())
                .name(facilityRequest.getName())
                .enable(Enable.ENABLE.name())
                .inventory(facilityRequest.getInventory())
                .price(facilityRequest.getPrice())
                .img(imgUrl)
                .build();

        Facility saveFacility = facilityRepository.save(facility);

        BillFacility billFacility = BillFacility.builder()
                .idFacility(saveFacility.getIdFacility())
                .quantity(saveFacility.getInventory())
                .day(LocalDate.now())
                .price(saveFacility.getPrice())
                .totalPrice(saveFacility.getPrice() * saveFacility.getInventory())
                .build();

        billFacilityRepository.save(billFacility);
        return saveFacility;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public List<Facility> getFacilityByIdStadium(Long idStadium) {
        return facilityRepository.findAllFacilityByIdStadium(idStadium);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Facility updateFacility(Long idFacility, FacilityUpdate facilityUpdate) {
        Facility facility = facilityRepository.findById(idFacility)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if(facilityUpdate.getUsable() < facility.getUsable()){
            throw new AppException(ErrorCode.IVALID_KEY);
        }
        int signalUsable = facilityUpdate.getUsable() - facility.getUsable();
        if(signalUsable > facility.getInventory()){
            throw new AppException(ErrorCode.QUANTITY_NOT_ENOUGH);
        }
        if(facilityUpdate.getUsable() > facility.getUsable()){
            facility.setUsable(facilityUpdate.getUsable());
            facility.setInventory(facility.getInventory() - signalUsable);
        }
        return facilityRepository.save(facility);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Facility removeFacility(Long idFacility) {
        Facility facility = facilityRepository.findById(idFacility)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        facility.setEnable(Enable.UNENABLE.name());
        return facilityRepository.save(facility);
    }
}
