package com.example.BE_DATN.repository;

import com.example.BE_DATN.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, String> {
    @Query("SELECT CASE WHEN Count(*) > 0 THEN true ELSE false END FROM Token " +
            "WHERE idToken = :idToken")
    boolean existsTokenById(@Param("idToken") String idToken);
}
