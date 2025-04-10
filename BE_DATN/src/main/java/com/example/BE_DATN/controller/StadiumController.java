package com.example.BE_DATN.controller;
import com.example.BE_DATN.dto.request.StadiumRequest;
import com.example.BE_DATN.dto.request.StadiumUpdate;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.StadiumRespone;
import com.example.BE_DATN.service.StadiumService;
import com.google.common.net.HttpHeaders;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/stadium")
public class StadiumController {
    @Autowired
    StadiumService stadiumService;

    @Autowired
    MinioClient minioClient;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiRespone<StadiumRespone> createStadium(
            @Valid @ModelAttribute StadiumRequest stadiumRequest,
            @RequestParam("img") MultipartFile file) throws IOException{
        return ApiRespone.<StadiumRespone>builder()
                .code(200)
                .message("Success")
                .result(stadiumService.createStadium(stadiumRequest,file))
                .build();
    }

    @GetMapping
    public ApiRespone<List<StadiumRespone>> getStadiums(){
        return ApiRespone.<List<StadiumRespone>>builder()
                .code(200)
                .result(stadiumService.getStadiums())
                .build();
    }

    @PutMapping("/{idStadium}")
    public ApiRespone<StadiumRespone> updateStadium(@PathVariable("idStadium") Long id_stadium,
                                                    @ModelAttribute StadiumUpdate stadiumUpdate){
        return ApiRespone.<StadiumRespone>builder()
                .code(200)
                .message("Success")
                .result(stadiumService.updateStadium(id_stadium,stadiumUpdate))
                .build();
    }
}
