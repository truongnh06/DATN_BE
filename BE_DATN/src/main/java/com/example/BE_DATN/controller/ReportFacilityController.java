package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.ReportFacilityRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.ReportFacilityRespone;
import com.example.BE_DATN.entity.ReportFacility;
import com.example.BE_DATN.service.ReportFacilityService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportfacility")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportFacilityController {
    @Autowired
    ReportFacilityService reportFacilityService;
    @PostMapping
    public  ApiRespone<ReportFacility> createReport(@ModelAttribute ReportFacilityRequest reportFacilityRequest){
        return ApiRespone.<ReportFacility>builder()
                .code(200)
                .message("Success")
                .result(reportFacilityService.createReport(reportFacilityRequest))
                .build();
    }

    @GetMapping("/{idStadium}")
    public ApiRespone<List<ReportFacilityRespone>> getReportByIdStadium(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<ReportFacilityRespone>>builder()
                .code(200)
                .message("Success")
                .result(reportFacilityService.getReportByIdStadium(idStadium))
                .build();
    }
}
