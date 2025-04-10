package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium,Long> {
    boolean existsByName(String name);
    Optional<Stadium> findById(Long id);
    Optional<Stadium> findByName(String name);
}
