package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.BillFacilityRequest;
import com.example.BE_DATN.dto.respone.BillFacilityRespone;
import com.example.BE_DATN.dto.respone.FacilityMonthlyRespone;
import com.example.BE_DATN.entity.BillFacility;

import java.util.List;

public interface BillFacilityService {
    public List<BillFacilityRespone> getBillFacilityByIdFacility(Long idFacility);
    public BillFacility createBillFacility(BillFacilityRequest request);
    public List<FacilityMonthlyRespone> getFacilityMonthly(Long idStadium);
}
