package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.FacilityRequest;
import com.example.BE_DATN.dto.request.FacilityUpdate;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.entity.Facility;
import com.example.BE_DATN.service.FacilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/facility")
@Slf4j
public class FacilityController {
    @Autowired
    FacilityService facilityService;

    @PostMapping
    public ApiRespone<Facility> createFacility(@ModelAttribute FacilityRequest facilityRequest,
                                               @RequestParam("img")MultipartFile file) throws IOException{
        return ApiRespone.<Facility>builder()
                .code(200)
                .message("Success")
                .result(facilityService.createFacility(facilityRequest,file))
                .build();
    }

    @GetMapping("/{idStadium}")
    public  ApiRespone<List<Facility>> getFacilityByIdStadium(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<Facility>>builder()
                .code(200)
                .message("Success")
                .result(facilityService.getFacilityByIdStadium(idStadium))
                .build();
    }
    @PutMapping("/{idFacility}")
    public ApiRespone<Facility> updateFacility(@PathVariable("idFacility") Long idFacility,
                                               @ModelAttribute FacilityUpdate facilityUpdate){
        return ApiRespone.<Facility>builder()
                .code(200)
                .message("Success")
                .result(facilityService.updateFacility(idFacility,facilityUpdate))
                .build();
    }

    @PutMapping("{idFacility}/enable")
    public ApiRespone<Facility> removeFacility(@PathVariable("idFacility") Long idFacility){
        return ApiRespone.<Facility>builder()
                .code(200)
                .message("Success")
                .result(facilityService.removeFacility(idFacility))
                .build();
    }
}
