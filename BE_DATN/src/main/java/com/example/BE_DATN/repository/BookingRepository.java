package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.BookingRespone;
import com.example.BE_DATN.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Query("SELECT new com.example.BE_DATN.dto.respone.BookingRespone(" +
            "b.idBooking, u.name, u.phoneNumber, f.name,s.name, p.price, " +
            "t.time, b.day, b.totalPrice, b.paymentStatus)" +
            "FROM Booking b " +
            "JOIN User u ON b.idUser = u.idUser " +
            "JOIN Price p ON p.idPrice = b.idPrice " +
            "JOIN Time t ON t.idTime = p.idTime " +
            "JOIN Field f ON f.idField = p.idField " +
            "JOIN Stadium s ON s.idStadium = f.idStadium")
    List<BookingRespone> findAllBooking();
}
