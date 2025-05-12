package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.ReportFacilityRequest;
import com.example.BE_DATN.dto.respone.ReportFacilityRespone;
import com.example.BE_DATN.entity.ReportFacility;

import java.util.List;

public interface ReportFacilityService {
    public ReportFacility createReport(ReportFacilityRequest reportFacilityRequest);
    public List<ReportFacilityRespone> getReportByIdStadium(Long idStadium);
}
