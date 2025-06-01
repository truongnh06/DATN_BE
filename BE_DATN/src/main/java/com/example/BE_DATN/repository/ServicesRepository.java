package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {
    @Query("SELECT CASE WHEN Count(*) > 0 THEN true ELSE false END FROM Services s " +
            "WHERE s.idStadium = :idStadium AND s.name = :name AND s.enable = 'ENABLE'")
    boolean existsByName(@Param("idStadium") Long idStadium,
                         @Param("name") String name);

    @Query("SELECT s FROM Services s WHERE s.idStadium = :idStadium AND s.enable = 'ENABLE'")
    List<Services> findByIdStadium(@Param("idStadium") Long idStadium);

    @Query("SELECT s FROM Services s WHERE s.idService = :idService " +
            "AND s.enable = 'ENABLE'")
    Services findByIdService(@Param("idService") Long idService);

    //kiểm tra sự tồn tại của service qua id
    @Query("SELECT CASE WHEN Count(*) > 0 THEN true ELSE false END FROM Services s " +
            "WHERE s.idService = :idService " +
            "AND s.enable = 'ENABLE'")
    boolean existsByIdServices(@Param("idService") Long idService);

    //lấy service theo idService
    @Query("SELECT s FROM Services s WHERE s.idService = :idService " +
            "AND s.enable = 'ENABLE'")
    Optional<Services> findServiceByID(@Param("idService") Long idService);
}
