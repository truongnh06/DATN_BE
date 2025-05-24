package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.request.TypeRequest;
import com.example.BE_DATN.entity.Type;
import com.example.BE_DATN.repository.TypeRepository;
import com.example.BE_DATN.service.TypeService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypeRepository typeRepository;

    @Override
    public Type createType(TypeRequest typeRequest) {
        Type type = Type.builder()
                .name(typeRequest.getName())
                .build();
        typeRepository.save(type);
        return type;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public List<Type> getTypes() {
        return typeRepository.findAll().stream().toList();
    }
}
