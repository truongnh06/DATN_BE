package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.InvoiceDayRespone;
import com.example.BE_DATN.dto.respone.ListInvoiceRespone;
import com.example.BE_DATN.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    @Query("SELECT CASE WHEN Count(*) > 0 THEN true ELSE false END FROM Invoice i " +
            "WHERE i.idBooking = :idBooking")
    boolean existsBookingByIdBooking(@Param("idBooking") Long idBooking);

    //tính doanh thu theo từng tháng trong năm theo idStadium
    @Query("SELECT MONTH(day) as month, YEAR(day) as year, COUNT(*) as total, SUM(totalPrice) as revenue " +
            "FROM Invoice " +
            "WHERE YEAR(day) = YEAR(CURDATE()) " +
            "AND idStadium = :idStadium " +
            "GROUP BY YEAR(day), MONTH(day) " +
            "ORDER BY year, month")
    List<Object[]> getListInvoiceByMonth(@Param("idStadium") Long idStadium);

    //tính doanh thu theo ngày mà người dùng đưa vào
    @Query("SELECT new com.example.BE_DATN.dto.respone.InvoiceDayRespone(" +
            "i.day, SUM(i.totalPrice) ) " +
            "FROM Invoice i " +
            "WHERE i.day = :day " +
            "AND i.idStadium = :idStadium " +
            "GROUP BY i.day")
    Optional<InvoiceDayRespone> getInvoiceDay(@Param("day")LocalDate day,
                                              @Param("idStadium") Long idStadium);
    //lấy list invoice trong ngày theo idStadium
    @Query("SELECT new com.example.BE_DATN.dto.respone.ListInvoiceRespone(" +
            "i.idInvoice, i.day, i.idStadium, u.name, i.idBooking, i.totalPrice) " +
            "FROM Invoice i JOIN User u ON i.idUser = u.idUser " +
            "WHERE i.idStadium = :idStadium " +
            "AND i.day = :day")
    List<ListInvoiceRespone> getListInvoiceDayByIdStadium(@Param("idStadium") Long idStadium,
                                                          @Param("day") LocalDate day);

    //tính tổng doanh thu theo tháng hiện tại
//    @Query("SELECT Month(day) as month, Year(day) as year, SUM(totalPrice) as total " +
//            "FROM Invoice WHERE Month(day) = month(curdate()), Year(day) = year(curdate()) " +
//            "group by month, year")
//
}
