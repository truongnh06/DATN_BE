package com.example.BE_DATN.service;

import com.example.BE_DATN.entity.District;

import java.util.List;

public interface DistrictService {
    public District createDistrict(String name);
    public List<District> getAllDistrict();
}
