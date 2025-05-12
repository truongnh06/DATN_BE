package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.FacilityRequest;
import com.example.BE_DATN.dto.request.FacilityUpdate;
import com.example.BE_DATN.entity.Facility;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FacilityService {
    public Facility createFacility(FacilityRequest facilityRequest,MultipartFile file);
    public List<Facility> getFacilityByIdStadium(Long idStadium);
    public Facility updateFacility(Long idFacility, FacilityUpdate facilityUpdate);
    public Facility removeFacility(Long idFacility);
}
