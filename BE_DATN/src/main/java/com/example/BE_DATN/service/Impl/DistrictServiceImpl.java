package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.entity.District;
import com.example.BE_DATN.repository.DistrictRepository;
import com.example.BE_DATN.service.DistrictService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    DistrictRepository districtRepository;

    @Override
    public District createDistrict(String name) {
        District district = District.builder()
                .name(name)
                .build();
        return districtRepository.save(district);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public List<District> getAllDistrict() {
        return districtRepository.getAllDistrict();
    }
}
