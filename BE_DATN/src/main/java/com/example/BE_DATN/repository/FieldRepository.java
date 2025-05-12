package com.example.BE_DATN.repository;

import com.example.BE_DATN.dto.respone.FieldRespone;
import com.example.BE_DATN.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field,Long> {
    @Query("SELECT CASE WHEN COUNT(f) = 1 THEN true ELSE false END FROM Field f WHERE f.name = :name AND f.idStadium = :idStadium AND f.idType = :idType")
    boolean existsUniqueByNameAndStadiumAndType(String name, Long idStadium, Long idType);

    Optional<Field> findById(Long id);

    @Query("SELECT new com.example.BE_DATN.dto.respone.FieldRespone(f.idField, f.name, s.name, f.img, t.name, f.status, f.enable) " +
            "FROM Field f " +
            "JOIN Stadium s ON f.idStadium = s.idStadium " +
            "JOIN Type t on f.idType = t.idType")
//    câu query sẽ ánh xạ theo thứ tự của FieldRespone
    List<FieldRespone> findAllFieldDetails();

    @Query("SELECT new com.example.BE_DATN.dto.respone.FieldRespone(f.idField ,f.name, s.name, f.img, t.name, f.status, f.enable) " +
            "FROM Field f " +
            "JOIN Stadium s ON f.idStadium = s.idStadium " +
            "JOIN Type t on f.idType = t.idType " +
            "WHERE f.idField = :idField")
    Optional<FieldRespone> findFieldDetailById(Long idField);
//lọc các idField của sân 7 , đã xuất hiện trong bảng IdField rồi thì k xuất hiện
    @Query("SELECT new com.example.BE_DATN.entity.Field(f.idField, f.idStadium, f.name, f.img, f.idType, f.status, f.enable) " +
            "FROM Field f " +
            "WHERE f.idType = :idType " +
            "AND f.idStadium = :idStadium " +
            "AND f.enable = 'ENABLE'" +
            "AND f.idField NOT IN (" +
            " SELECT i.idField7 FROM IdField i WHERE i.idField7 IS NOT NULL" +
            ")")
    List<Field> findFieldByIdTypeAndIdStadium(@Param("idType") Long idType,@Param("idStadium") Long idStadium);

//    kiểm tra xem id sân người dùng truyền vào có là sân 7 không
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Field f WHERE f.idField = :idField AND f.idType = :idType")
    boolean existsByIdFieldAndIdType(Long idField, Long idType);

// kiểm tra xem id sân người dùng truyền vào có là id sân 11 không
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Field f WHERE f.idField = :idField AND f.idType = :idType")
    boolean existsByIdFieldAndIdType11(Long idField, Long idType);

// tìm kiếm sân 11 xem sân nào xuất hiện 4 lần trong bảng IdField thì không hiển thị ra vì nó đã được ghép thành sân to rồi
    @Query("SELECT new com.example.BE_DATN.entity.Field(f.idField, f.idStadium, f.name, f.img, f.idType, f.status, f.enable) " +
            "FROM Field f " +
            "WHERE f.idType = :idType AND f.idStadium = :idStadium " +
            "AND f.enable = 'ENABLE'" +
            "AND f.idField NOT IN (" +
            " SELECT idField11 FROM IdField GROUP BY idField11 HAVING COUNT(*) = 4" +
            ")")
    List<Field> findFieldsByIdType11AndIdStadium(@Param("idType") Long idType, @Param("idStadium") Long idStadium);

// lấy ra trạng thái của sân dựa vào idPrice
    @Query("SELECT f.status FROM Price p JOIN Field f ON p.idField = f.idField WHERE p.idPrice = :idPrice")
    String getStatusByIdPrice(@Param("idPrice") Long idPrice);

    //lấy sân theo idStadium và idType
    @Query("SELECT new com.example.BE_DATN.dto.respone.FieldRespone(" +
            "f.idField, f.name, s.name, f.img, t.name, f.status, f.enable) " +
            "FROM Field f JOIN Stadium s ON f.idStadium = s.idStadium " +
            "JOIN Type t ON t.idType = f.idType " +
            "WHERE f.idStadium = :idStadium " +
            "AND f.idType = :idType " +
            "AND f.enable = 'ENABLE'")
    List<FieldRespone> getFieldByIdStadiumAndIdTypeAndEnable(@Param("idStadium") Long idStadium,
                                                             @Param("idType") Long idType);

    //Kiểm tra xem idField nhập vào có là sân 11 không
    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Field f " +
            "WHERE f.idType = 2 " +
            "AND f.idField = :idField")
    boolean existsField11ByIdField(@Param("idField") Long idField);

    //kiểm tra xem sân có tồn tại trong bảng field không
    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Field f " +
            "WHERE f.idField = :idField")
    boolean existsFieldByIdField(@Param("idField") Long idField);

    //lấy danh sách sân theo idStadium và idType (list 7)
    @Query("SELECT f FROM Field f WHERE f.idStadium = :idStadium " +
            "AND f.idType = :idType " +
            "AND f.enable = 'ENABLE'")
    List<Field> getListFieldByIdTypeAndIdStadium(@Param("idType") Long idType,
                                                 @Param("idStadium") Long idStadium);
}
