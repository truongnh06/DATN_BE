package com.example.BE_DATN.service.Impl;
import com.example.BE_DATN.Mapper.StadiumMapper;
import com.example.BE_DATN.dto.request.StadiumRequest;
import com.example.BE_DATN.dto.request.StadiumUpdate;
import com.example.BE_DATN.dto.respone.StadiumRespone;
import com.example.BE_DATN.entity.Stadium;
import com.example.BE_DATN.enums.Enable;
import com.example.BE_DATN.enums.StadiumStatus;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.StadiumRepository;
import com.example.BE_DATN.service.MinioService;
import com.example.BE_DATN.service.StadiumService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StadiumServiceImpl implements StadiumService {
    @Autowired
    StadiumRepository stadiumRepository;
    @Autowired
    MinioClient minioClient;

    @Autowired
    StadiumMapper stadiumMapper;

    @Autowired
    MinioService minioService;

    @Value("${minio.bucketName}")
    String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Stadium createStadium(StadiumRequest stadiumRequest, MultipartFile file) {
        if(stadiumRepository.existsByName(stadiumRequest.getName())){throw new AppException(ErrorCode.NAME_EXISTED);
        }
        String imageUrl = minioService.uploadFile(file,bucketName,"Stadium");
        Stadium stadium = Stadium.builder()
                .name(stadiumRequest.getName())
                .address(stadiumRequest.getAddress())
                .idDistrict(stadiumRequest.getIdDistrict())
                .phoneNumber(stadiumRequest.getPhoneNumber())
                .enable(Enable.ENABLE.name())
                .img(imageUrl)
                .status(StadiumStatus.ACTIVE.name())
                .build();
        return stadiumRepository.save(stadium);

    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public List<StadiumRespone> getStadiums() {
        return  stadiumRepository.getAllStadium();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public StadiumRespone updateStadium(Long id, StadiumUpdate stadiumUpdate) {
        Stadium stadium = stadiumRepository.findById(id).orElseThrow(() ->new AppException(ErrorCode.NOT_FOUND));
        stadiumMapper.updateStadium(stadium,stadiumUpdate);
        stadiumRepository.save(stadium);
        StadiumRespone stadiumRespone =StadiumRespone.builder()
                .idStadium(stadium.getIdStadium())
                .name(stadium.getName())
                .address(stadium.getAddress())
                .phoneNumber(stadium.getPhoneNumber())
                .enable(stadium.getEnable())
                .img(stadium.getImg())
                .status(stadium.getStatus())
                .build();
        return stadiumRespone;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Stadium updateStatus(Long idStadium) {
        Stadium stadium = stadiumRepository.findById(idStadium)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        String status = stadium.getStatus().equals(StadiumStatus.ACTIVE.name()) ?
                StadiumStatus.INACTIVE.name() : StadiumStatus.ACTIVE.name();
        stadium.setStatus(status);
        return stadiumRepository.save(stadium);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public List<StadiumRespone> getAllStadiumEnable() {
        return stadiumRepository.getAllStadiumEnable();
    }

}
