package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.ReportFacilityRespone;
import com.example.BE_DATN.entity.ReportFacility;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportFacilityRepository extends JpaRepository<ReportFacility,Long> {
    //Lấy reportFacility ra theo idStadium và enable
    @Query("SELECT new com.example.BE_DATN.dto.respone.ReportFacilityRespone(" +
            "r.idReportFacility, f.name, r.day, fi.name, r.quantity, s.name, u.name, u.phoneNumber, r.note) " +
            "FROM ReportFacility r JOIN Facility f ON r.idFacility = f.idFacility " +
            "JOIN User u ON u.idUser = r.idUser " +
            "JOIN Field fi ON r.idField = fi.idField " +
            "JOIN StatusFacility s ON s.idStatusFacility = r.idStatusFacility " +
            "WHERE fi.idStadium = :idStadium " +
            "AND r.enable = 'ENABLE' " +
            "AND s.name = 'DAMAGED' " +
            "ORDER BY r.day ASC")
    List<ReportFacilityRespone> findReportByIdStadium(@Param("idStadium") Long idStadium);

    @Modifying
    @Transactional
    @Query("Delete from ReportFacility r where r.idReportFacility = :idReportFacility")
    void deleteReport(@Param("idReportFacility") Long idReportFacility);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM ReportFacility r " +
            "WHERE r.idReportFacility = :idReportFacility")
    boolean existsByIdReportFacility(@Param("idReportFacility") Long idReportFacility);
}
