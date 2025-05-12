package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.BillServiceRespone;
import com.example.BE_DATN.entity.BillService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillServiceRepository extends JpaRepository<BillService, Long> {
    @Query("SELECT new com.example.BE_DATN.dto.respone.BillServiceRespone(" +
            "s.name, b.quantity, s.unit, b.costPrice, b.total, b.day ) " +
            "FROM BillService b JOIN Services s ON b.idService = s.idService " +
            "WHERE b.idService = :idService")
    List<BillServiceRespone> getListBillService(@Param("idService") Long idService);

    //tính tiền nhập dịch vụ theo tháng
    @Query("SELECT Month(day) as month, Year(day) as year, SUM(total) as total " +
            "FROM BillService b JOIN Services s ON b.idService = s.idService " +
            "WHERE s.idStadium = :idStadium " +
            "GROUP BY Year(day), Month(day) " +
            "ORDER BY year,month")
    List<Object[]> getServiceMonthlyByIdStadium(@Param("idStadium") Long idStadium);
}
