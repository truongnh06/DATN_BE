package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.SellOffRespone;
import com.example.BE_DATN.entity.SellOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SellOffRepository extends JpaRepository<SellOff,Long> {
    @Query("SELECT new com.example.BE_DATN.dto.respone.SellOffRespone(" +
            "f.name, s.day, fi.name, s.quantity, s.price, u.name, u.phoneNumber, r.note, sf.name) " +
            "FROM SellOff s JOIN ReportFacility r ON s.idReportFacility = r.idReportFacility " +
            "JOIN User u ON u.idUser = s.idUser " +
            "JOIN Facility f ON f.idFacility = r.idFacility " +
            "JOIN Stadium st ON st.idStadium = f.idStadium " +
            "JOIN Field fi ON fi.idField = r.idField " +
            "JOIN StatusFacility sf ON sf.idStatusFacility = r.idStatusFacility " +
            "where f.idStadium = :idStadium " +
            "ORDER BY s.day ASC")
    List<SellOffRespone> getListSellOff(@Param("idStadium") Long idStadium);

    //tính tiền sửa chưa theo tháng
    @Query("SELECT Month(s.day) as month, Year(s.day) as year, SUM(s.price) as total " +
            "FROM SellOff s JOIN ReportFacility r ON s.idReportFacility = r.idReportFacility " +
            "JOIN Facility f ON f.idFacility = r.idFacility " +
            "WHERE f.idStadium = :idStadium " +
            "GROUP BY Year(s.day), Month(s.day) " +
            "ORDER BY year, month")
    List<Object[]> getSellOffMonthly(@Param("idStadium") Long idStadium);
}
