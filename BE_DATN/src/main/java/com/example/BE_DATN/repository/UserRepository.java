package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.UserRespone;
import com.example.BE_DATN.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT CASE WHEN Count(*) > 0 THEN true ELSE false END FROM " +
            "User u WHERE u.idUser = :idUser " +
            "AND u.enable = 'ENABLE'")
    boolean existsByIdUser(@Param("idUser") Long idUser);

    //lấy userRespone theo idUser
    @Query("SELECT new com.example.BE_DATN.dto.respone.UserRespone(" +
            "u.idUser, u.name, u.phoneNumber, u.password, u.email, r.name, u.enable) " +
            "FROM User u JOIN Role r ON u.idRole = r.idRole " +
            "WHERE u.idUser = :idUser")
    Optional<UserRespone> getUserByIdUser(@Param("idUser") Long idUser);

    //Tìm User qua Email
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> getUserByEmail(@Param("email") String email);

    //kiểm tra xem người dùng nhập mật khẩu đúng chưa
    @Query("SELECT u FROM User u WHERE u.idUser = :idUser AND u.password = :password")
    Optional<User> findByIdUserAndPassword(@Param("idUser") Long idUser, @Param("password") String password);

    //tìm xem có User nào tên là ADMIN chưa
    @Query("SELECT u FROM User u WHERE u.name = 'ADMIN'")
    Optional<User> findADMIN();
}
