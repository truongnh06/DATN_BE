package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium,Long> {
    boolean existsByName(String name);
    Optional<Stadium> findById(Long id);
    Optional<Stadium> findByName(String name);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Stadium s WHERE s.idStadium = :idStadium")
    boolean existsByIdStadium(@Param("idStadium") Long idStadium);
}
