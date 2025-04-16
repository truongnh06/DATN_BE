package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    @Query("SELECT CASE WHEN COUNT(so) > 0 THEN true ELSE false END FROM ServiceOrder so " +
            "JOIN Booking b ON b.idBooking = so.idBooking " +
            "JOIN Price p ON p.idPrice = b.idPrice " +
            "JOIN Field f ON f.idField = p.idField " +
            "JOIN Services sv ON so.idService = sv.idService " +
            "WHERE f.idStadium = sv.idStadium " +
            "AND so.idBooking = :idBooking " +
            "AND so.idService = :idService")
    boolean existsServiceByStadium(@Param("idBooking") Long idBooking,
                                   @Param("idService") Long idService);
}
