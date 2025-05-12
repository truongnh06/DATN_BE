package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.entity.IdStatusMatching;
import com.example.BE_DATN.repository.IdStatusMatchingRepository;
import com.example.BE_DATN.service.IdStatusMatchingService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdStatusMatchingServiceImpl implements IdStatusMatchingService {
    @Autowired
    IdStatusMatchingRepository idStatusMatchingRepository;

    @Override
    public IdStatusMatching create(String name) {
        IdStatusMatching idStatusMatching = IdStatusMatching.builder()
                .name(name)
                .build();
        return idStatusMatchingRepository.save(idStatusMatching);
    }
}
