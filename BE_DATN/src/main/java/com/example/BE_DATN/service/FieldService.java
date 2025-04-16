package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.FieldRequest;
import com.example.BE_DATN.dto.request.FieldUpdate;
import com.example.BE_DATN.dto.respone.FieldRespone;
import com.example.BE_DATN.entity.Field;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FieldService {
    public FieldRespone createField(FieldRequest fieldRequest, MultipartFile file);
    public List<FieldRespone> getFields();
    public FieldRespone update(Long id, FieldUpdate fieldUpdate, MultipartFile file);
    public List<Field> getFieldsByIdType(Long idType, Long idStadium);
    public Field removeField(Long idField);
    public List<Field> getFiedlsByIdType2(Long idType, Long idStadium);
}
