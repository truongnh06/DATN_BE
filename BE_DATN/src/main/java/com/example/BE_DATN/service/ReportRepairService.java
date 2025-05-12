package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.ReportRepairRequest;
import com.example.BE_DATN.dto.respone.RepairMonthlyRespone;
import com.example.BE_DATN.dto.respone.ReportRepairRespone;
import com.example.BE_DATN.entity.ReportRepair;

import java.util.List;

public interface ReportRepairService {
    public ReportRepair createReportRepair(ReportRepairRequest request);
    public List<ReportRepairRespone> getAllReportRepairRespone(Long idStadium);
    public List<RepairMonthlyRespone> getRepairMonthly(Long idStadium);
}
