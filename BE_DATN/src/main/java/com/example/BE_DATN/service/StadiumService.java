package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.StadiumRequest;
import com.example.BE_DATN.dto.request.StadiumUpdate;
import com.example.BE_DATN.dto.respone.StadiumRespone;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StadiumService {
    public StadiumRespone createStadium(StadiumRequest stadiumRequest, MultipartFile file);
    public List<StadiumRespone> getStadiums();
    public StadiumRespone updateStadium(Long id, StadiumUpdate stadiumUpdate);
}
