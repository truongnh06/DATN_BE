package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.respone.BillServiceMonthlyRespone;
import com.example.BE_DATN.dto.respone.BillServiceRespone;
import com.example.BE_DATN.entity.BillService;

import java.util.List;

public interface BillServiceService {
    public BillService createBillService(Long idService,double costPrice, int quantity);
    public List<BillServiceRespone> getListBillService(Long idService);
    public List<BillServiceMonthlyRespone> getServiceMonthlyByIdStadium(Long idStadium);
}
