package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.MatchingRequest;
import com.example.BE_DATN.dto.respone.MatchingRespone;
import com.example.BE_DATN.entity.Matching;

import java.util.List;

public interface MatchingService {
    public Matching createMatching(MatchingRequest request);
    public List<MatchingRespone> getMatchingByIdStadium(Long idStadium);
    public Matching AcceptMatching(Long idMatching,Long idUserB);
    public List<MatchingRespone> getMatchByIdStadium(Long idStadium);
    public Matching unableMatching(Long idMatching, Long idUser);
    public Matching CancelMatching(Long idMatching, Long idUser, String reason);
}
