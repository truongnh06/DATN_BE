package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
