package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.BookingRespone;
import com.example.BE_DATN.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Query("SELECT new com.example.BE_DATN.dto.respone.BookingRespone(" +
            "b.idBooking, f.name, ty.name, ti.time, b.day, b.paymentStatus, " +
            "s.name, s.address, s.phoneNumber, u.name, u.phoneNumber, b.totalPrice) " +
            "FROM Booking b " +
            "JOIN Price p ON b.idPrice = p.idPrice " +
            "JOIN Field f ON f.idField = p.idField " +
            "JOIN Type ty ON f.idType = ty.idType " +
            "JOIN Time ti ON p.idTime = ti.idTime " +
            "JOIN User u ON u.idUser = b.idUser " +
            "JOIN Stadium s ON s.idStadium = f.idStadium " +
            "WHERE f.idStadium = :idStadium " +
            "AND b.enable = 'ENABLE' " +
            "ORDER BY b.day ASC")
    List<BookingRespone> findBookingByIdStadium(@Param("idStadium") Long idStadium);

    //sử dụng để hiển thị lịch ra calender
    @Query("SELECT new com.example.BE_DATN.dto.respone.BookingRespone(" +
            "b.idBooking, f.name, ty.name, ti.time, b.day, b.paymentStatus, " +
            "s.name, s.address, s.phoneNumber, u.name, u.phoneNumber, b.totalPrice) " +
            "FROM Booking b " +
            "JOIN Price p ON b.idPrice = p.idPrice " +
            "JOIN Field f ON f.idField = p.idField " +
            "JOIN Type ty ON f.idType = ty.idType " +
            "JOIN Time ti ON p.idTime = ti.idTime " +
            "JOIN User u ON u.idUser = b.idUser " +
            "JOIN Stadium s ON s.idStadium = f.idStadium " +
            "WHERE f.idField = :idField " +
            "AND b.enable = 'ENABLE' " +
            "AND b.paymentStatus = 'PAID' " +
            "ORDER BY b.day ASC")
    List<BookingRespone> findBookingByIdField(@Param("idField") Long idField);

    //kiểm tra xem có đúng người dùng đã đặt sân ở giờ này, ngày này , sân này không
    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Booking b JOIN Price p " +
            "ON b.idPrice = p.idPrice " +
            "JOIN Time t ON t.idTime = p.idTime " +
            "WHERE b.paymentStatus = 'PAID' " +
            "AND b.enable = 'ENABLE' " +
            "AND p.idField = :idField " +
            "AND b.day = :day " +
            "AND t.idTime = :idTime " +
            "AND b.idUser = :idUser")
    boolean existsBooking(@Param("idField") Long idField, @Param("day")LocalDate day,
                          @Param("idTime")Long idTime, @Param("idUser") Long idUser);

    //kiểm tra xem có tồn tại theo idBooking truyền vào không (Enable, Paid)
    @Query("SELECT CASE WHEN Count(*) > 0 THEN true ELSE false END FROM Booking b " +
            "WHERE b.idBooking = :idBooking " +
            "AND b.enable = 'ENABLE'" +
            "AND b.paymentStatus = 'PAID'")
    boolean existsBookingByIdBooking(@Param("idBooking") Long idBooking);

    //kiểm tra xem có lịch đặt sân nào trong tương lai không để remove sân
    @Query("SELECT CASE WHEN Count(*) > 0 THEN true ELSE false END FROM Booking b " +
            "JOIN Price p on p.idPrice = b.idPrice " +
            "WHERE p.idField = :idField " +
            "AND b.day >= CURRENT_DATE " +
            "AND b.enable = 'ENABLE' " +
            "AND b.paymentStatus = 'PAID'")
    boolean existsBookingInFutureByIdField(@Param("idField") Long idField);
}
