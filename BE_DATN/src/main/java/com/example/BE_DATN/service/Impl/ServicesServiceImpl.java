package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.request.ServicesRequest;
import com.example.BE_DATN.dto.request.ServicesUpdate;
import com.example.BE_DATN.dto.respone.ServicesRespone;
import com.example.BE_DATN.entity.Services;
import com.example.BE_DATN.entity.Stadium;
import com.example.BE_DATN.enums.Enable;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.ServicesRepository;
import com.example.BE_DATN.repository.StadiumRepository;
import com.example.BE_DATN.service.ServicesService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
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

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServicesServiceImpl implements ServicesService {
    @Autowired
    StadiumRepository stadiumRepository;
    @Autowired
    ServicesRepository serviceRepository;
    @Autowired
    MinioClient minioClient;
    @Value("${minio.bucketName3}")
    String bucketName;
    @Value("${minio.url}")
    private String minioUrl;
    @Override
    public ServicesRespone createService(ServicesRequest request, MultipartFile file) {
        if(!stadiumRepository.existsByIdStadium(request.getIdStadium())){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if(serviceRepository.existsByName(request.getName())){throw new AppException(ErrorCode.NAME_EXISTED);}
        String imageUrl = null;
        try{
            String filename = "Service_" + file.getOriginalFilename();
            
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if(!found){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            imageUrl = String.format("%s/%s/%s",minioUrl,bucketName,filename);
        } catch (Exception e){
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
        Services services = Services.builder()
                .idStadium(request.getIdStadium())
                .unit(request.getUnit())
                .name(request.getName())
                .enable(Enable.ENABLE.name())
                .quantity(request.getQuantity())
                .costPrice(request.getCostPrice())
                .retailPrice(request.getRetailPrice())
                .img(imageUrl)
                .build();
        serviceRepository.save(services);
        ServicesRespone servicesRespone = ServicesRespone.builder()
                .idService(services.getIdService())
                .nameStadium(stadiumRepository.findById(services.getIdStadium()).map(Stadium::getName).orElse(null))
                .name(services.getName())
                .unit(services.getUnit())
                .img(services.getImg())
                .costPrice(services.getCostPrice())
                .enable(services.getEnable())
                .retailPrice(services.getRetailPrice())
                .quantitySold(services.getQuantitySold())
                .quantity(services.getQuantity())
                .build();
        return servicesRespone;
    }

    @Override
    public Services removeServices(Long idService) {
        Services services = serviceRepository.findById(idService).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        services.setEnable(Enable.UNENABLE.name());
        return serviceRepository.save(services);
    }

    @Override
    public ServicesRespone updateServices(Long idService, ServicesUpdate servicesUpdate) {
        Services services = serviceRepository.findById(idService)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if(servicesUpdate.getName() != null
                && !servicesUpdate.getName().isBlank()
                && !servicesUpdate.getName().equals(services.getName())){
            if(serviceRepository.existsByName(servicesUpdate.getName())){
                throw new AppException(ErrorCode.NAME_EXISTED);
            }
            services.setName(servicesUpdate.getName());
        }
        services.setRetailPrice(servicesUpdate.getRetailPrice());
        services.setCostPrice(servicesUpdate.getCostPrice());
        services.setQuantity(servicesUpdate.getQuantity());
        services.setQuantitySold(servicesUpdate.getQuantitySold());

        serviceRepository.save(services);

        return ServicesRespone.builder()
                .idService(services.getIdService())
                .nameStadium(stadiumRepository.findById(services.getIdStadium()).map(Stadium::getName).orElse(null))
                .name(services.getName())
                .img(services.getImg())
                .costPrice(services.getCostPrice())
                .enable(services.getEnable())
                .retailPrice(services.getRetailPrice())
                .quantitySold(services.getQuantitySold())
                .quantity(services.getQuantity())
                .build();
    }

    @Override
    public List<Services> getServicesByIdStadium(Long idStadium) {
        return serviceRepository.findByIdStadium(idStadium);
    }
}
