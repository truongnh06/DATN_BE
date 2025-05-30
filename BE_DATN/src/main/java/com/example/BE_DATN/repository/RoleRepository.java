package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);
    Optional<Role> findByName(String name);

    @Query("SELECT r.name FROM Role r WHERE r.idRole = :idRole")
    String getNameRole(@Param("idRole") Long idRole);
}
