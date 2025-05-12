package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.StatusFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusFacilityRepository extends JpaRepository<StatusFacility, Long> {
    @Query("SELECT s FROM StatusFacility s WHERE s.name = :name")
    Optional<StatusFacility> getStatusByName(@Param("name") String name);
}
