package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.TimeRequest;
import com.example.BE_DATN.entity.Time;

import java.time.LocalDate;
import java.util.List;

public interface TimeService {
    public Time createTime(TimeRequest timeRequest);
    public List<Time> getTime();
    public  List<Time> getTimeByIdFieldandDay(Long idField, LocalDate day);
}
