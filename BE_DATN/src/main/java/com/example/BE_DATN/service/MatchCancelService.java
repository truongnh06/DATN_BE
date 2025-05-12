package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.respone.MatchCancelRespone;
import com.example.BE_DATN.entity.MatchCancel;

import java.time.LocalDate;
import java.util.List;

public interface MatchCancelService {
    public MatchCancel createMatchCancel(Long idMatching, Long idUserCancel, String reason, LocalDate day);
    public List<MatchCancelRespone> getTopUserCancel();
}
