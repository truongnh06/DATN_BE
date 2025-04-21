package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.ServicesRequest;
import com.example.BE_DATN.dto.request.ServicesUpdate;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.ServicesRespone;
import com.example.BE_DATN.entity.Services;
import com.example.BE_DATN.service.ServicesService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/services")
public class ServicesController {
    @Autowired
    ServicesService servicesService;
    @PostMapping
    ApiRespone<ServicesRespone> createServices(@ModelAttribute ServicesRequest request,
                                               @RequestParam("img")MultipartFile file ) throws IOException {
        return ApiRespone.<ServicesRespone>builder()
                .code(200)
                .message("Success")
                .result(servicesService.createService(request, file))
                .build();
    }

    @PutMapping("/{idService}/enable")
    ApiRespone<Services> removeServices(@PathVariable("idService") Long idService){
        return ApiRespone.<Services>builder()
                .code(200)
                .message("Success")
                .result(servicesService.removeServices(idService))
                .build();
    }

    @PutMapping("/{idService}")
    ApiRespone<ServicesRespone> updateServices(@PathVariable("idService") Long idService
            , @ModelAttribute ServicesUpdate servicesUpdate){
        return ApiRespone.<ServicesRespone>builder()
                .code(200)
                .message("Success")
                .result(servicesService.updateServices(idService,servicesUpdate))
                .build();
    }
    @GetMapping("/{idStadium}")
    ApiRespone<List<Services>> getServicesByIdStadium(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<Services>>builder()
                .code(200)
                .message("Success")
                .result(servicesService.getServicesByIdStadium(idStadium))
                .build();
    }

    @GetMapping("/{idService}/Service")
    ApiRespone<Services> getServiceByIdService(@PathVariable("idService") Long idService){
        return ApiRespone.<Services>builder()
                .code(200)
                .message("Success")
                .result(servicesService.getServicesByIdService(idService))
                .build();
    }
}
