package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District,Long> {
    @Query("SELECT d FROM District d")
    List<District> getAllDistrict();
}
