package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.request.TimeRequest;
import com.example.BE_DATN.entity.Time;
import com.example.BE_DATN.repository.FieldRepository;
import com.example.BE_DATN.repository.TimeRepository;
import com.example.BE_DATN.service.TimeService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeServiceImpl implements TimeService {
    @Autowired
    TimeRepository timeRepository;

    @Autowired
    FieldRepository fieldRepository;

    @Override
    public Time createTime(TimeRequest timeRequest) {
        Time time = Time.builder()
                .time(timeRequest.getTime())
                .build();
        timeRepository.save(time);
        return time;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public List<Time> getTime() {
        return timeRepository.findAll().stream().toList();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public List<Time> getTimeByIdFieldandDay(Long idField, LocalDate day) {
        if(fieldRepository.existsField11ByIdField(idField)){
            return timeRepository.findByIdField11AndDay(day,idField);
        } else {
            return timeRepository.findByIdFieldAndDay(idField, day);
        }
    }
}
