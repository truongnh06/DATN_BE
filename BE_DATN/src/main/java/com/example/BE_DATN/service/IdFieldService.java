package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.IdFieldRequest;
import com.example.BE_DATN.entity.IdField;

public interface IdFieldService {
    public IdField createIdField(IdFieldRequest idFieldRequest);
}
