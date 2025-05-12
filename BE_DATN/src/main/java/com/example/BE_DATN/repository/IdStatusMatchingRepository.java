package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.IdStatusMatching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IdStatusMatchingRepository extends JpaRepository<IdStatusMatching,Long> {
    @Query("SELECT i.idStatusMatching FROM IdStatusMatching i WHERE i.name = :name")
    Long getIdByName(@Param("name") String name);
}
