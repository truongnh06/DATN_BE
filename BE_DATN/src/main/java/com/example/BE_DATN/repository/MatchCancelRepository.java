package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.MatchCancelRespone;
import com.example.BE_DATN.entity.MatchCancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchCancelRepository extends JpaRepository<MatchCancel, Long> {
    @Query("SELECT new com.example.BE_DATN.dto.respone.MatchCancelRespone(m.idUserCancel, u.name, COUNT(m)) " +
            "FROM MatchCancel m JOIN User u ON m.idUserCancel = u.idUser " +
            "GROUP BY m.idUserCancel, u.name " +
            "ORDER BY COUNT(m) DESC")
    List<MatchCancelRespone> getMatchCancelStatistics();

}
