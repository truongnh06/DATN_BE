package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.BookingRespone;
import com.example.BE_DATN.dto.respone.RefundRespone;
import com.example.BE_DATN.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    //lấy booking theo idStadium and idUser
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
            "AND b.idUser = :idUser " +
            "AND b.day >= current_date " +
            "ORDER BY b.day ASC")
    List<BookingRespone> getBookingByIdStadiumAndIdUser(@Param("idStadium") Long idStadium,
                                                        @Param("idUser") Long idUser);

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

    //kiểm tra xem mã booking đó đã được đặt đối chưa
    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM Booking b " +
            "JOIN Price p ON b.idPrice = p.idPrice " +
            "JOIN Matching m ON m.idField = p.idField " +
            "WHERE m.day = b.day " +
            "AND p.idField = m.idField " +
            "AND p.idTime = m.idTime " +
            "AND b.enable = 'ENABLE' " +
            "AND b.paymentStatus = 'PAID' " +
            "AND m.enable = 'ENABLE' " +
            "AND b.idBooking = :idBooking")
    boolean existsBookingOnMatching(@Param("idBooking") Long idBooking);

    //lấy danh sách refund
    @Query("SELECT new com.example.BE_DATN.dto.respone.RefundRespone( " +
            "b.idBooking, u.name, u.phoneNumber, b.totalPrice) " +
            "FROM Booking b JOIN User u " +
            "ON b.idUser = u.idUser " +
            "JOIN Price p ON p.idPrice = b.idPrice " +
            "JOIN Field f ON f.idField = p.idField " +
            "JOIN Stadium s ON s.idStadium = f.idStadium " +
            "WHERE b.paymentStatus = 'PAID' " +
            "AND b.enable = 'UNENABLE' " +
            "AND f.idStadium = :idStadium " +
            "AND u.enable = 'ENABLE'")
    List<RefundRespone> getRefund(@Param("idStadium") Long idStadium);

    //lấy booking theo idBooking
    @Query("SELECT b from Booking b WHERE b.idBooking = :idBooking " +
            "AND b.paymentStatus = 'PAID' " +
            "AND b.enable = 'UNENABLE'")
    Optional<Booking> getBookingRefund(@Param("idBooking") Long idBooking);
}
