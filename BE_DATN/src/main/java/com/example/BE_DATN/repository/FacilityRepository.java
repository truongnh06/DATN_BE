package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    @Query("SELECT CASE WHEN COUNT(f) = 1 THEN true ELSE false END FROM Facility f " +
            "WHERE f.name = :name " +
            "AND f.idStadium = :idStadium")
    boolean existsByName(@Param("name") String name, @Param("idStadium") Long idStadium);

    @Query("SELECT f FROM Facility f WHERE f.idStadium = :idStadium AND f.enable = 'ENABLE'")
    List<Facility> findAllFacilityByIdStadium(@Param("idStadium") Long idStadium);

    //tìm xem idField và idFacility có cùng một stadium không
    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM " +
            "Facility fa JOIN Stadium s ON fa.idStadium = s.idStadium " +
            "JOIN Field f ON f.idStadium = s.idStadium " +
            "WHERE fa.idFacility = :idFacility " +
            "AND f.idField = :idField")
    boolean existsFieldAndFacilitySameStadium(@Param("idField") Long idField,
                                              @Param("idFacility") Long idFacility);
}
