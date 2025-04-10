package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.Mapper.FieldMapper;
import com.example.BE_DATN.dto.request.FieldRequest;
import com.example.BE_DATN.dto.request.FieldUpdate;
import com.example.BE_DATN.dto.respone.FieldRespone;
import com.example.BE_DATN.entity.Field;
import com.example.BE_DATN.entity.Stadium;
import com.example.BE_DATN.entity.Type;
import com.example.BE_DATN.enums.Enable;
import com.example.BE_DATN.enums.StadiumStatus;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.FieldRepository;
import com.example.BE_DATN.repository.StadiumRepository;
import com.example.BE_DATN.repository.TypeRepository;
import com.example.BE_DATN.service.FieldService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldServiceImpl implements FieldService {
    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    MinioClient minioClient;

    @Autowired
    FieldMapper fieldMapper;

    @Autowired
    StadiumRepository stadiumRepository;
    @Autowired
    TypeRepository typeRepository;
    @Value("${minio.bucketName2}")
    String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    @Override
    public FieldRespone createField(FieldRequest fieldRequest, MultipartFile file) {
        String imageUrl = null;
        try {
            String filename = "Field_" + file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            imageUrl = String.format("%s/%s/%s", minioUrl, bucketName, filename);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
        Field field = Field.builder()
                .idStadium(stadiumRepository.findByName(fieldRequest.getNameStadium())
                        .map(Stadium::getIdStadium).orElse(null))
                .name(fieldRequest.getName())
                .img(imageUrl)
                .idType(typeRepository.findByName(fieldRequest.getNameType())
                        .map(Type::getIdType).orElse(null))
                .status(StadiumStatus.ACTIVE.name())
                .enable(Enable.ENABLE.name())
                .build();
        if (fieldRepository.existsUniqueByNameAndStadiumAndType(
                field.getName(), field.getIdStadium(), field.getIdType())) {
            throw new AppException(ErrorCode.NAME_EXISTED);
        }
        fieldRepository.save(field);
        return FieldRespone.builder()
                .name(field.getName())
                .nameStadium(fieldRequest.getNameStadium())
                .img(field.getImg())
                .nameType(fieldRequest.getNameType())
                .status(field.getStatus())
                .enable(field.getEnable())
                .build();
    }

    @Override
    public List<FieldRespone> getFields() {
        return fieldRepository.findAllFieldDetails().stream().map(field -> {
            FieldRespone fieldRespone = FieldRespone.builder()
                    .idField(field.getIdField())
                    .name(field.getName())
                    .img(field.getImg())
                    .nameStadium(field.getNameStadium())
                    .nameType(field.getNameType())
                    .status(field.getStatus())
                    .enable(field.getEnable())
                    .build();
            return fieldRespone;
        }).toList();
    }

    @Override
    public FieldRespone update(Long idField, FieldUpdate fieldUpdate, MultipartFile file) {
        Field field = fieldRepository.findById(idField).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if (fieldUpdate.getName() != null
                && !fieldUpdate.getName().isBlank()
                && !fieldUpdate.getName().equals(field.getName())) {
            if (fieldRepository.existsUniqueByNameAndStadiumAndType(fieldUpdate.getName()
                    , field.getIdStadium(), field.getIdType())) {
                throw new AppException(ErrorCode.NAME_EXISTED);
            }
            field.setName(fieldUpdate.getName());
        }
        if (fieldUpdate.getStatus() != null
                && !fieldUpdate.getStatus().isBlank()
                && !fieldUpdate.getStatus().equals(field.getStatus())) {
            field.setStatus(fieldUpdate.getStatus());
        }
        if (file != null && !file.isEmpty()) {
            try {
                String filename = "Field_" + file.getOriginalFilename();
                InputStream inputStream = file.getInputStream();
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(filename)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
                String imageUrl = String.format("%s/%s/%s", minioUrl, bucketName, filename);
                if (!imageUrl.equals(field.getImg())) {
                    field.setImg(imageUrl);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error uploading file: " + e.getMessage());
            }
        } else {
            field.setImg(field.getImg());
        }
        fieldRepository.save(field);
        return fieldRepository.findFieldDetailById(idField).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
    }

    @Override
    public List<Field> getFieldsByIdType(Long idType, Long idStadium) {
         return fieldRepository.findFieldByIdTypeAndIdStadium(idType,idStadium);
    }

    @Override
    public Field removeField(Long idField) {
        Field field = fieldRepository.findById(idField).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        field.setEnable(Enable.UNENABLE.name());
        return fieldRepository.save(field);
    }

}