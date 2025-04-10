package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.IdField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IdFieldRepository extends JpaRepository<IdField,Long> {
    @Query("SELECT COUNT(i) FROM IdField i WHERE i.idField11 = :idField11")
    Long countByIdField11(@Param("idField11") Long idField11);
}
