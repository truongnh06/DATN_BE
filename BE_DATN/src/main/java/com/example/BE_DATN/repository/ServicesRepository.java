package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {
    boolean existsByName(String name);

    @Query("SELECT s FROM Services s WHERE s.idStadium = :idStadium")
    List<Services> findByIdStadium(@Param("idStadium") Long idStadium);
}
