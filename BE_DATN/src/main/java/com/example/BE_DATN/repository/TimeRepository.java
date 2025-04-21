package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimeRepository extends JpaRepository<Time,Long> {
    Optional<Time> findByTime(LocalTime time);
    Optional<Time> findByIdTime(Long id);
    //lọc thời gian trong một ngày của một sân 7 xem giờ nào còn trống
    @Query("SELECT t FROM Time t " +
            "WHERE t.idTime NOT IN (" +
            "SELECT p.idTime FROM Booking b join Price p " +
            "ON b.idPrice = p.idPrice " +
            "WHERE p.idField = :idField " +
            "AND b.day = :day " +
            "AND b.paymentStatus = 'PAID' " +
            "AND b.enable = 'ENABLE')")
    List<Time> findByIdFieldAndDay(@Param("idField") Long idField, @Param("day")LocalDate day);

    //lọc thời gian trong một ngày của một sân 11 xem giờ nào còn trống
    @Query("SELECT t FROM Time t " +
            "WHERE t.idTime NOT IN (" +
            "SELECT p.idTime FROM Price p JOIN Booking b " +
            "ON p.idPrice = b.idPrice " +
            "WHERE p.idField IN (" +
            "SELECT i.idField7 FROM IdField i " +
            "WHERE i.idField11 = :idField11 )" +
            "AND b.day = :day " +
            "AND b.paymentStatus = 'PAID' " +
            "AND b.enable = 'ENABLE')")
    List<Time> findByIdField11AndDay(@Param("day") LocalDate day, @Param("idField11") Long idField11);
}
