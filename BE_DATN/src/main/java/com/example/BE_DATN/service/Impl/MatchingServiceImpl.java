package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.request.MatchingRequest;
import com.example.BE_DATN.dto.respone.MatchingRespone;
import com.example.BE_DATN.entity.Matching;
import com.example.BE_DATN.enums.Enable;
import com.example.BE_DATN.enums.StatusMatch;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.*;
import com.example.BE_DATN.service.MatchCancelService;
import com.example.BE_DATN.service.MatchingService;
import com.example.BE_DATN.service.MessageService;
import jakarta.transaction.Transactional;
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
public class MatchingServiceImpl implements MatchingService {
    @Autowired
    MatchingRepository matchingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    IdStatusMatchingRepository idStatusMatchingRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    MatchCancelService matchCancelService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public Matching createMatching(MatchingRequest request) {
        if(!userRepository.existsByIdUser(request.getIdUserA())){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if(!fieldRepository.existsFieldByIdField(request.getIdField())){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if(request.getDay().isBefore(LocalDate.now())){
            throw new AppException(ErrorCode.BOOKING_DAY_INVALID);
        }
        if(!bookingRepository.existsBooking(request.getIdField(), request.getDay()
                , request.getIdTime(), request.getIdUserA())){
            throw new AppException(ErrorCode.BOOKING_INVALID);
        }
        if(matchingRepository.existsMatchingByIdUser(request.getIdUserA(),
                request.getDay(), request.getIdTime())){
            throw new AppException(ErrorCode.CONFLICT_SCHEDULE);
        }
        if(matchingRepository.existsMatching(request.getIdUserA(), request.getIdField(),
                request.getIdTime(), request.getDay())){
            throw new AppException(ErrorCode.MATCHING_EXISTS);
        }
        Matching matching = Matching.builder()
                .idUserA(request.getIdUserA())
                .idField(request.getIdField())
                .day(request.getDay())
                .idTime(request.getIdTime())
                .enable(Enable.ENABLE.name())
                .notes(request.getNotes())
                .idStatusMatching(idStatusMatchingRepository.getIdByName(StatusMatch.PENDING.name()))
                .build();
        return matchingRepository.save(matching);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public List<MatchingRespone> getMatchingByIdStadium(Long idStadium) {
        return matchingRepository.getMatchingResponeByIdStadium(idStadium,1L);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Transactional
    @Override
    public Matching AcceptMatching(Long idMatching, Long idUserB) {
        Matching matching = matchingRepository.findById(idMatching)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if(matching.getIdUserA().equals(idUserB)){
            throw new AppException(ErrorCode.IVALID_KEY);
        }
        if(!userRepository.existsByIdUser(idUserB)){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if(matching.getDay().isBefore(LocalDate.now())){
            throw new AppException(ErrorCode.BOOKING_DAY_INVALID);
        }
        if(matchingRepository.existsMatchingByIdUser(idUserB, matching.getDay(), matching.getIdTime())){
            throw new AppException(ErrorCode.CONFLICT_SCHEDULE);
        }
        matching.setIdStatusMatching(2L);
        matching.setIdUserB(idUserB);

        Matching saveMatching = matchingRepository.save(matching);

        String message = "Match on" + " " + saveMatching.getDay();
        messageService.createMessage(matching.getIdUserA(), message,
                saveMatching.getDay(), saveMatching.getIdMatching());
        return saveMatching;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<MatchingRespone> getMatchByIdStadium(Long idStadium) {
        return matchingRepository.getMatchingResponeByIdStadium(idStadium, 2L);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public Matching unableMatching(Long idMatching, Long idUser) {
        Matching matching = matchingRepository.findById(idMatching)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if(!userRepository.existsByIdUser(idUser)){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if(!matching.getIdUserA().equals(idUser)){
            throw new AppException(ErrorCode.IVALID_KEY);
        }
        if(matching.getDay().isBefore(LocalDate.now())){
            throw new AppException(ErrorCode.BOOKING_DAY_INVALID);
        }
        matching.setEnable(Enable.UNENABLE.name());
        return matchingRepository.save(matching);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Transactional
    @Override
    public Matching CancelMatching(Long idMatching, Long idUser, String reason) {
        Matching matching = matchingRepository.findMatchByIdMatching(idMatching)
                .orElseThrow(() -> new AppException(ErrorCode.MATCH_NOT_FOUND));
        if(!userRepository.existsByIdUser(idUser)){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        if(!matching.getDay().isAfter(LocalDate.now())){
            throw new AppException(ErrorCode.BOOKING_DAY_INVALID);
        }
        String message = "Cancel the match on " + " " + matching.getDay();

        if(idUser.equals(matching.getIdUserA())){
            matchCancelService.createMatchCancel(matching.getIdMatching(),
                    matching.getIdUserA(), reason, matching.getDay());
            matching.setEnable(Enable.UNENABLE.name());
            matching.setIdStatusMatching(3L);
            messageService.createMessage(matching.getIdUserB(), message,
                    matching.getDay(), matching.getIdMatching());

        } else if (idUser.equals(matching.getIdUserB())) {
            matchCancelService.createMatchCancel(matching.getIdMatching(),
                    matching.getIdUserB(), reason, matching.getDay());
            matching.setIdUserB(null);
            matching.setIdStatusMatching(1L);
            messageService.createMessage(matching.getIdUserA(), message,
                    matching.getDay(), matching.getIdMatching());
        } else {
            throw new AppException(ErrorCode.IVALID_KEY);
        }
        return matchingRepository.save(matching);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public List<MatchingRespone> getMatchByIdStadiumAndIdUser(Long idStadium, Long idUser) {
        return matchingRepository.getMatchingResponeByIdStadiumAndIdUser(idStadium,2L, idUser);
    }
}
