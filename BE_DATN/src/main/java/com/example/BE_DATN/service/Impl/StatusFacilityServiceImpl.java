package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.entity.StatusFacility;
import com.example.BE_DATN.repository.StatusFacilityRepository;
import com.example.BE_DATN.service.StatusFacilityService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusFacilityServiceImpl implements StatusFacilityService {
    @Autowired
    StatusFacilityRepository statusFacilityRepository;
    @Override
    public StatusFacility createStatusFacility(String name) {
        StatusFacility statusFacility = StatusFacility.builder()
                .name(name)
                .build();
        return statusFacilityRepository.save(statusFacility);
    }
}
