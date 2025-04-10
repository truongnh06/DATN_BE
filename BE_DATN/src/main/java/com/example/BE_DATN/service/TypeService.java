package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.TypeRequest;
import com.example.BE_DATN.entity.Type;

import java.util.List;

public interface TypeService {
    public Type createType(TypeRequest typeRequest);
    public List<Type> getTypes();
}
