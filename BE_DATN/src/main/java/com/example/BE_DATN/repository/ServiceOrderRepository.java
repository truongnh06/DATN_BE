package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.ServiceOrderDtoRespone;
import com.example.BE_DATN.dto.respone.ServiceOrderRespone;
import com.example.BE_DATN.entity.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    @Query("SELECT CASE WHEN f.idStadium = s.idStadium THEN true ELSE false END " +
            "FROM Booking b " +
            "JOIN Price p ON b.idPrice = p.idPrice " +
            "JOIN Field f ON p.idField = f.idField " +
            "JOIN Services s ON s.idService = :idService " +
            "WHERE b.idBooking = :idBooking " +
            "AND b.enable ='ENABLE'")
    boolean existsServiceByStadium(@Param("idBooking") Long idBooking,
                                   @Param("idService") Long idService);

    @Query("SELECT new com.example.BE_DATN.dto.respone.ServiceOrderRespone(so.idServiceOrder, so.idBooking," +
            " sv.name, so.quantity, so.totalPrice, sv.retailPrice) " +
            "FROM ServiceOrder so JOIN Services sv " +
            "ON so.idService = sv.idService " +
            "JOIN Booking b ON b.idBooking = so.idBooking " +
            "WHERE so.idBooking = :idBooking " +
            "AND b.enable = 'ENABLE'")
    List<ServiceOrderRespone> findServicesByIdBooking(@Param("idBooking") Long idBooking);
    //lấy entity serviceOrder theo idBooking
    List<ServiceOrder> findByIdBooking(@Param("idBooking") Long idBooking);

    //xóa ServiceOrder theo idBooking
    @Modifying
    @Transactional
    @Query("DELETE FROM ServiceOrder s WHERE s.idBooking = :idBooking")
    void deleteByIDBooking(@Param("idBooking") Long idBooking);

    //lấy hết các ServiceOrderRespone ra
    @Query("SELECT new com.example.BE_DATN.dto.respone.ServiceOrderDtoRespone(" +
            "so.idServiceOrder, b.idBooking, so.idService, f.name, s.name, so.quantity, t.time, " +
            "b.day, so.totalPrice) " +
            "FROM ServiceOrder so join Booking b on so.idBooking = b.idBooking " +
            "join Services s on so.idService = s.idService " +
            "join Price p on b.idPrice = p.idPrice " +
            "join Field f on f.idField = p.idField " +
            "join Time t on t.idTime = p.idTime " +
            "where f.idType = :idType " +
            "and s.idStadium = :idStadium " +
            "and b.enable = 'ENABLE' " +
            "ORDER BY b.day ASC")
    List<ServiceOrderDtoRespone> findServiceOrderDtoByIdStadiumAndIdType(@Param("idType") Long idType,
                                                                         @Param("idStadium") Long idStadium);

    //lấy ra serviceOrderRespone theo idServiceOrder
    @Query("SELECT new com.example.BE_DATN.dto.respone.ServiceOrderDtoRespone(" +
            "so.idServiceOrder, b.idBooking, so.idService, f.name, s.name, so.quantity, t.time, " +
            "b.day, so.totalPrice) " +
            "FROM ServiceOrder so join Booking b on so.idBooking = b.idBooking " +
            "join Services s on so.idService = s.idService " +
            "join Price p on b.idPrice = p.idPrice " +
            "join Field f on f.idField = p.idField " +
            "join Time t on t.idTime = p.idTime " +
            "where so.idServiceOrder = :idServiceOrder")
    Optional<ServiceOrderDtoRespone> findByIdServiceOrder(@Param("idServiceOrder") Long idServiceOrder);

    //xóa serviceOrder theo idServiceOrder
    @Modifying
    @Transactional
    @Query("DELETE from ServiceOrder s WHERE s.idServiceOrder = :idServiceOrder")
    void deleteByIdServiceOrder(@Param("idServiceOrder") Long idServiceOrder);

    //kiểm tra xem có đơn hàng của idService trong tương lai không
    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM ServiceOrder s JOIN " +
            "Booking b ON b.idBooking = s.idBooking " +
            "WHERE b.day >= CURRENT_DATE " +
            "AND s.idService = :idService")
    boolean existsFutureOrdersByServiceId(@Param("idService") Long idService);
}
