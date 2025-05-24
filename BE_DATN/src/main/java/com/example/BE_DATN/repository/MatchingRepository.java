package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.MatchingRespone;
import com.example.BE_DATN.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching,Long> {
    @Query("SELECT new com.example.BE_DATN.dto.respone.MatchingRespone(" +
            "m.idMatching, ua.name, ub.name, ua.phoneNumber, ub.phoneNumber, f.name, t.name, " +
            "m.day, ti.time, m.notes, sm.name, s.name, s.address, p.price, m.enable) " +
            "FROM Matching m JOIN User ua ON ua.idUser = m.idUserA " +
            "LEFT JOIN User ub ON ub.idUser = m.idUserB " +
            "JOIN Field f ON f.idField = m.idField " +
            "JOIN Time ti ON ti.idTime = m.idTime " +
            "JOIN Stadium s ON s.idStadium = f.idStadium " +
            "JOIN Type t ON t.idType = f.idType " +
            "JOIN IdStatusMatching sm ON sm.idStatusMatching = m.idStatusMatching " +
            "JOIN Price p on p.idField = m.idField and p.idTime = m.idTime " +
            "WHERE f.idStadium = :idStadium " +
            "AND f.enable = 'ENABLE' " +
            "AND f.status = 'ACTIVE' " +
            "AND m.day >= current_date() " +
            "AND m.enable = 'ENABLE' " +
            "AND m.idStatusMatching = :idStatusMatching " +
            "ORDER BY m.day ASC, ti.time ASC")
    List<MatchingRespone> getMatchingResponeByIdStadium(@Param("idStadium") Long idStadium ,
                                                        @Param("idStatusMatching") Long idStatusMatching);

    //kiểm tra xem sân theo ngày , giờ cụ thể đã được đăng tìm đối chưa(chặn người dùng tạo nhiều lời mời trùng lặp)
    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM Matching m " +
            "WHERE m.idUserA = :idUser " +
            "AND m.idField = :idField " +
            "AND m.enable = 'ENABLE' " +
            "AND m.idTime = :idTime " +
            "AND m.day = :day")
    boolean existsMatching(@Param("idUser") Long idUser, @Param("idField") Long idField,
                           @Param("idTime") Long idTime, @Param("day")LocalDate day);

    //kiểm tra xem có tồn tại dựa theo idMatching
    @Query("SELECT CASE WHEN Count(*) > 0 THEN true ELSE false END FROM Matching m " +
            "WHERE m.idMatching = :idMatching " +
            "AND m.day >= current_date() " +
            "AND m.enable = 'ENABLE'")
    boolean existsMatchingByIdMatching(@Param("idMatching") Long idMatching);

    //kiểm tra lịch matching để người dùng kiểm tra xem có bị trùng không
    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Matching m " +
            "WHERE (m.idUserA = :idUser or m.idUserB = :idUser) " +
            "AND m.day = :day " +
            "AND m.idTime = :idTime " +
            "AND m.enable = 'ENABLE'")
    boolean existsMatchingByIdUser(@Param("idUser") Long idUser, @Param("day") LocalDate day,
                                   @Param("idTime") Long idTime);

    //Tìm kiếm matching theo idMatching
    @Query("SELECT m FROM Matching m WHERE m.idMatching = :idMatching " +
            "AND m.enable = 'ENABLE'")
    Optional<Matching> findMatchByIdMatching(@Param("idMatching") Long idMatching);

    @Query("SELECT new com.example.BE_DATN.dto.respone.MatchingRespone(" +
            "m.idMatching, ua.name, ub.name, ua.phoneNumber, ub.phoneNumber, f.name, t.name, " +
            "m.day, ti.time, m.notes, sm.name, s.name, s.address, p.price, m.enable) " +
            "FROM Matching m JOIN User ua ON ua.idUser = m.idUserA " +
            "LEFT JOIN User ub ON ub.idUser = m.idUserB " +
            "JOIN Field f ON f.idField = m.idField " +
            "JOIN Time ti ON ti.idTime = m.idTime " +
            "JOIN Stadium s ON s.idStadium = f.idStadium " +
            "JOIN Type t ON t.idType = f.idType " +
            "JOIN IdStatusMatching sm ON sm.idStatusMatching = m.idStatusMatching " +
            "JOIN Price p on p.idField = m.idField and p.idTime = m.idTime " +
            "WHERE f.idStadium = :idStadium " +
            "AND (m.idUserA = :idUser OR m.idUserB = :idUser) " +
            "AND f.enable = 'ENABLE' " +
            "AND f.status = 'ACTIVE' " +
            "AND m.day >= current_date() " +
            "AND m.enable = 'ENABLE' " +
            "AND m.idStatusMatching = :idStatusMatching " +
            "ORDER BY m.day ASC, ti.time ASC")
    List<MatchingRespone> getMatchingResponeByIdStadiumAndIdUser(@Param("idStadium") Long idStadium ,
                                                        @Param("idStatusMatching") Long idStatusMatching,
                                                        @Param("idUser") Long idUser);
}
