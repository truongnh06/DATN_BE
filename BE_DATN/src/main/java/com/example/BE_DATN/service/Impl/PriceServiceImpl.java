package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.Mapper.PriceMapper;
import com.example.BE_DATN.dto.request.PriceRequest;
import com.example.BE_DATN.dto.request.PriceUpdate;
import com.example.BE_DATN.dto.respone.PriceRespone;
import com.example.BE_DATN.entity.Field;
import com.example.BE_DATN.entity.Price;
import com.example.BE_DATN.entity.Time;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.FieldRepository;
import com.example.BE_DATN.repository.PriceRepository;
import com.example.BE_DATN.repository.TimeRepository;
import com.example.BE_DATN.service.PriceService;
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
public class PriceServiceImpl implements PriceService {
    @Autowired
    PriceRepository priceRepository;
    @Autowired
    PriceMapper priceMapper;
    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    TimeRepository timeRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public PriceRespone createPrice(PriceRequest request) {
        Price price = priceMapper.toPrice(request);
        if(priceRepository.existsUniqueByFieldAndTime(price.getIdField(),price.getIdTime())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }
        priceRepository.save(price);
        PriceRespone priceRespone = priceMapper.toPriceRespone(price);
        priceRespone.setTime(timeRepository.findByIdTime(price.getIdTime()).map(Time::getTime).orElse(null));
        priceRespone.setNameField(fieldRepository.findById(price.getIdField()).map(Field::getName).orElse("null"));
        return  priceRespone;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public List<Price> getPrceByField(Long idField) {
        return priceRepository.getPriceByIdField(idField);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public PriceRespone updatePrice(Long idPrice, PriceUpdate priceUpdate) {
        Price price = priceRepository.findByIdPrice(idPrice).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        price.setPrice(priceUpdate.getPrice());
        priceRepository.save(price);
        PriceRespone priceRespone = priceMapper.toPriceRespone(price);
        priceRespone.setTime(timeRepository.findByIdTime(price.getIdTime()).map(Time::getTime).orElse(null));
        priceRespone.setNameField(fieldRepository.findById(price.getIdField()).map(Field::getName).orElse("null"));
        return  priceRespone;
    }
}
