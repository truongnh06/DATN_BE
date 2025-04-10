package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Optional;

public interface TimeRepository extends JpaRepository<Time,Long> {
    Optional<Time> findByTime(LocalTime time);
    Optional<Time> findByIdTime(Long id);
}
