package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.request.FieldRequest;
import com.example.BE_DATN.dto.request.IdFieldRequest;
import com.example.BE_DATN.entity.IdField;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.FieldRepository;
import com.example.BE_DATN.repository.IdFieldRepository;
import com.example.BE_DATN.service.IdFieldService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdFieldServiceImpl implements IdFieldService {
    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    IdFieldRepository idFieldRepository;

    @Override
    public IdField createIdField(IdFieldRequest idFieldRequest) {
        if(!fieldRepository.existsByIdFieldAndIdType(idFieldRequest.getIdField7(), 1L)){
            throw new AppException(ErrorCode.NOT_FOUND_FIELD_7);
        }
        if(!fieldRepository.existsByIdFieldAndIdType11(idFieldRequest.getIdField11(), 2L)){
            throw new AppException(ErrorCode.NOT_FOUND_FIELD_11);
        }
        Long count = idFieldRepository.countByIdField11(idFieldRequest.getIdField11());
        if(count >= 4){
            throw new AppException(ErrorCode.MAXIMUN_FIELD11_REACHED);
        }
        IdField idField = IdField.builder()
                .idField7(idFieldRequest.getIdField7())
                .idField11(idFieldRequest.getIdField11())
                .build();
        return idFieldRepository.save(idField);
    }
}
