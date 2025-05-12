package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.ReportRepairRespone;
import com.example.BE_DATN.entity.ReportRepair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepairRepository extends JpaRepository<ReportRepair, Long> {
    //lấy danh sách reportRepairRepone theo idStadium và enable
    @Query("SELECT new com.example.BE_DATN.dto.respone.ReportRepairRespone(" +
            "rr.idReportRepair, f.name, rf.day, fi.name, rr.quantity, rr.price, rr.day, u.name, u.phoneNumber, rf.note, " +
            "f.idFacility) " +
            "FROM ReportRepair rr JOIN ReportFacility rf ON rr.idReportFacility = rf.idReportFacility " +
            "JOIN User u ON u.idUser = rr.idUser " +
            "JOIN Facility f ON f.idFacility = rf.idFacility " +
            "JOIN Field fi ON rf.idField = fi.idField " +
            "WHERE fi.idStadium = :idStadium " +
            "AND rr.enable = 'ENABLE' " +
            "ORDER BY rf.day ASC")
    List<ReportRepairRespone> getReportRepairRespone(@Param("idStadium") Long idStadium);

    //tính tổng tiền sửa chưa hàng tháng trong năm
    @Query("SELECT Month(s.day) as month, Year(s.day) as year, SUM(s.price) as total " +
            "FROM ReportRepair s JOIN ReportFacility r ON s.idReportFacility = r.idReportFacility " +
            "JOIN Facility f ON f.idFacility = r.idFacility " +
            "WHERE f.idStadium = :idStadium " +
            "GROUP BY Year(s.day), Month(s.day) " +
            "ORDER BY year, month")
    List<Object[]> getRepairMonthly(@Param("idStadium") Long idStadium);
}
