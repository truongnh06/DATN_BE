package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.ServicesRequest;
import com.example.BE_DATN.dto.request.ServicesUpdate;
import com.example.BE_DATN.dto.respone.ServicesRespone;
import com.example.BE_DATN.entity.Services;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServicesService {
    public ServicesRespone createService(ServicesRequest request, MultipartFile file);
    public Services removeServices(Long idService);
    public ServicesRespone updateServices(Long idService, ServicesUpdate servicesUpdate);
    public List<Services> getServicesByIdStadium(Long idStadium);
    public Services getServicesByIdService(Long idService);
}
