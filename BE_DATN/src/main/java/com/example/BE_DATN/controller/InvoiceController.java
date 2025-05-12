package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.InvoiceDayRespone;
import com.example.BE_DATN.dto.respone.InvoiceRespone;
import com.example.BE_DATN.dto.respone.ListInvoiceRespone;
import com.example.BE_DATN.entity.Invoice;
import com.example.BE_DATN.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/invoice")
@Slf4j
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @PostMapping
    public ApiRespone<Invoice> createInvoice(@RequestParam("day")LocalDate day,
                                             @RequestParam("idStadium") Long idStadium,
                                             @RequestParam("idUser") Long idUser,
                                             @RequestParam("idBooking") Long idBooking,
                                             @RequestParam("totalPrice") double totalPrice){
        return ApiRespone.<Invoice>builder()
                .code(200)
                .message("Success")
                .result(invoiceService.createInvoice(day, idStadium, idUser, idBooking, totalPrice))
                .build();
    }

    @GetMapping("/{idStadium}")
    public ApiRespone<List<InvoiceRespone>> getInvoiceByMonth(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<InvoiceRespone>>builder()
                .code(200)
                .message("Success")
                .result(invoiceService.getInvoiceByMonth(idStadium))
                .build();
    }
    @GetMapping("/{idStadium}/{day}")
    public ApiRespone<InvoiceDayRespone> getInvoiceByDay(@PathVariable("idStadium") Long idStadium,
                                                         @PathVariable("day") LocalDate day){
        return ApiRespone.<InvoiceDayRespone>builder()
                .code(200)
                .message("Success")
                .result(invoiceService.getInvoiceDay(day, idStadium))
                .build();
    }

    @GetMapping("/{idStadium}/{day}/daily")
    public ApiRespone<List<ListInvoiceRespone>> getInvoiceDayByIdStadium(@PathVariable("idStadium") Long idStadium,
                                                                         @PathVariable("day") LocalDate day){
        return ApiRespone.<List<ListInvoiceRespone>>builder()
                .code(200)
                .message("Success")
                .result(invoiceService.getInvoiceDayByIdStadium(idStadium,day))
                .build();
    }
}
