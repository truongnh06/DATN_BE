package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    //kiểm tra xem người dùng có tin nào chưa đọc theo hiện tại và tương lai
    @Query("SELECT CASE WHEN Count(*) > 0 THEN true ELSE false END FROM Message m " +
            "WHERE m.idUser = :idUser " +
            "AND m.isRead = 'NO' " +
            "AND m.day >= current_date()")
    boolean existsMessageNotYet(@Param("idUser") Long idUser);

    //lấy danh sách tin nhắn chưa đọc theo hiện tại và tương lai
    @Query("SELECT m FROM Message m WHERE m.idUser = :idUser " +
            "AND m.isRead = 'NO' " +
            "AND m.day >= current_date()")
    List<Message> getListMessageNotYetRead(@Param("idUser") Long idUser);
}
