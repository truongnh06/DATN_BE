package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.BillServiceMonthlyRespone;
import com.example.BE_DATN.dto.respone.BillServiceRespone;
import com.example.BE_DATN.entity.BillService;
import com.example.BE_DATN.service.BillServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billservice")
@Slf4j
public class BillServiceController {
    @Autowired
    BillServiceService billServiceService;

    @PostMapping
    public ApiRespone<BillService> createBillService(@RequestParam("idService") Long idService,
                                                     @RequestParam("costPrice") double costPrice,
                                                     @RequestParam("quantity") int quantity){
        return ApiRespone.<BillService>builder()
                .code(200)
                .message("Success")
                .result(billServiceService.createBillService(idService, costPrice, quantity))
                .build();
    }

    @GetMapping("/{idService}")
    public ApiRespone<List<BillServiceRespone>> getListBillService(@PathVariable("idService") Long idService){
        return ApiRespone.<List<BillServiceRespone>>builder()
                .code(200)
                .message("Success")
                .result(billServiceService.getListBillService(idService))
                .build();
    }

    @GetMapping("/{idStadium}/monthly")
    public ApiRespone<List<BillServiceMonthlyRespone>> getServiceMonthlyByIdStadium(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<BillServiceMonthlyRespone>>builder()
                .code(200)
                .message("Success")
                .result(billServiceService.getServiceMonthlyByIdStadium(idStadium))
                .build();
    }
}
