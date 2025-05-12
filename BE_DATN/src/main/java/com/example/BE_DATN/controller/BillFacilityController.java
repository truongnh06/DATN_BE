package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.BillFacilityRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.BillFacilityRespone;
import com.example.BE_DATN.dto.respone.FacilityMonthlyRespone;
import com.example.BE_DATN.entity.BillFacility;
import com.example.BE_DATN.service.BillFacilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billfacility")
@Slf4j
public class BillFacilityController {
    @Autowired
    BillFacilityService billFacilityService;

    @PostMapping
    public ApiRespone<BillFacility> createBillFacility(@ModelAttribute BillFacilityRequest request){
        return ApiRespone.<BillFacility>builder()
                .code(200)
                .message("Success")
                .result(billFacilityService.createBillFacility(request))
                .build();
    }

    @GetMapping("/{idFacility}")
    public ApiRespone<List<BillFacilityRespone>> getBillFacilityByIdFacility(@PathVariable("idFacility") Long idFacility){
        return ApiRespone.<List<BillFacilityRespone>>builder()
                .code(200)
                .message("Success")
                .result(billFacilityService.getBillFacilityByIdFacility(idFacility))
                .build();
    }
    @GetMapping("/{idStadium}/monthly")
    public ApiRespone<List<FacilityMonthlyRespone>> getFacilityMonthly(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<FacilityMonthlyRespone>>builder()
                .code(200)
                .message("Success")
                .result(billFacilityService.getFacilityMonthly(idStadium))
                .build();
    }
}
