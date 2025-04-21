package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.BookingRespone;
import com.example.BE_DATN.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
            "AND b.enable = 'ENABLE'")
    List<BookingRespone> findBookingByIdStadium(@Param("idStadium") Long idStadium);
}
