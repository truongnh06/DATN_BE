package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.BillFacilityRespone;
import com.example.BE_DATN.entity.BillFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BillFacilityRepository extends JpaRepository<BillFacility,Long> {
    @Query("SELECT new com.example.BE_DATN.dto.respone.BillFacilityRespone(" +
            "f.name, b.quantity, b.price, b.day, b.totalPrice) " +
            "FROM BillFacility b JOIN Facility f ON f.idFacility = b.idFacility " +
            "WHERE b.idFacility = :idFacility")
    List<BillFacilityRespone> getBillFacilityByIdFacility(@Param("idFacility") Long idFacility);
    //lấy tổng tiền facility theo tháng
    @Query("SELECT Month(day) as month, Year(day) as year, Sum(totalPrice) as total " +
            "FROM BillFacility b JOIN Facility f on b.idFacility = f.idFacility " +
            "WHERE f.idStadium = :idStadium " +
            "GROUP BY Year(day),Month(day) " +
            "ORDER BY year, month")
    List<Object[]> getFacilityMonthly(@Param("idStadium") Long idStadium);
}
