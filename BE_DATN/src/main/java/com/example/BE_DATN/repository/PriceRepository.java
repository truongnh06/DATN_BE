package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price,Long> {
    @Query("SELECT CASE WHEN COUNT(p) = 1 THEN true ELSE false END FROM Price p WHERE p.idField = :idField AND p.idTime = :idTime")
    boolean existsUniqueByFieldAndTime(Long idField, Long idTime);

    List<Price> getPriceByIdField(Long idField);
    Optional<Price> findByIdPrice(Long idPrice);
}
