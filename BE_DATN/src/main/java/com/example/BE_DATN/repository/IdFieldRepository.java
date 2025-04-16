package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.IdField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IdFieldRepository extends JpaRepository<IdField,Long> {
    //kiểm tra sân 11 tần suất xuất hiện bao nhiêu lần
    @Query("SELECT COUNT(i) FROM IdField i WHERE i.idField11 = :idField11")
    Long countByIdField11(@Param("idField11") Long idField11);
    //kiểm tra sân 7 đã xuất hiện trong bảng IdField chưa
    @Query("SELECT COUNT(*) > 0 FROM IdField i WHERE i.idField7 = :idField7")
    Boolean existsByIdField7(@Param("idField7") Long idField7);
}
