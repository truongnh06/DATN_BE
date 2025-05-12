package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.ReportRepairRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.RepairMonthlyRespone;
import com.example.BE_DATN.dto.respone.ReportRepairRespone;
import com.example.BE_DATN.entity.ReportRepair;
import com.example.BE_DATN.service.ReportRepairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportrepair")
@Slf4j
public class ReportRepairController {
    @Autowired
    ReportRepairService reportRepairService;

    @PostMapping
    public ApiRespone<ReportRepair> createReportRepair(@ModelAttribute ReportRepairRequest reportRepairRequest){
        return ApiRespone.<ReportRepair>builder()
                .code(200)
                .message("Success")
                .result(reportRepairService.createReportRepair(reportRepairRequest))
                .build();
    }

    @GetMapping("/{idStadium}")
    public ApiRespone<List<ReportRepairRespone>> getAllReportRepairRespone(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<ReportRepairRespone>>builder()
                .code(200)
                .message("Success")
                .result(reportRepairService.getAllReportRepairRespone(idStadium))
                .build();
    }

    @GetMapping("/{idStadium}/monthly")
    public ApiRespone<List<RepairMonthlyRespone>> getRepairMonthly(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<RepairMonthlyRespone>>builder()
                .code(200)
                .message("Success")
                .result(reportRepairService.getRepairMonthly(idStadium))
                .build();
    }
}
