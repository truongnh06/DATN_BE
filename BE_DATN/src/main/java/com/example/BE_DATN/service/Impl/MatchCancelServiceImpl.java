package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.respone.MatchCancelRespone;
import com.example.BE_DATN.entity.MatchCancel;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.MatchCancelRepository;
import com.example.BE_DATN.repository.MatchingRepository;
import com.example.BE_DATN.repository.UserRepository;
import com.example.BE_DATN.service.MatchCancelService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchCancelServiceImpl implements MatchCancelService {
    @Autowired
    MatchingRepository matchingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MatchCancelRepository matchCancelRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public MatchCancel createMatchCancel(Long idMatching, Long idUserCancel, String reason, LocalDate day) {
        if(!userRepository.existsByIdUser(idUserCancel)){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        if(!matchingRepository.existsMatchingByIdMatching(idMatching)){
            throw new AppException(ErrorCode.MATCH_NOT_FOUND);
        }
        MatchCancel matchCancel = MatchCancel.builder()
                .idMatching(idMatching)
                .idUserCancel(idUserCancel)
                .reason(reason)
                .day(day)
                .build();
        return matchCancelRepository.save(matchCancel);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public List<MatchCancelRespone> getTopUserCancel() {
        return matchCancelRepository.getMatchCancelStatistics();
    }
}
