package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.SellOffRequest;
import com.example.BE_DATN.dto.respone.SellOffMonthlyRespone;
import com.example.BE_DATN.dto.respone.SellOffRespone;
import com.example.BE_DATN.entity.SellOff;

import java.util.List;

public interface SellOffService {
    public SellOff createSellOff(SellOffRequest request);
    public List<SellOffRespone> getListSellOff(Long idStadium);
    public List<SellOffMonthlyRespone> getSellOffMonthly(Long idStadium);
}
