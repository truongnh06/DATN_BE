package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.FieldRequest;
import com.example.BE_DATN.dto.request.FieldUpdate;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.FieldEmptyRespone;
import com.example.BE_DATN.dto.respone.FieldRespone;
import com.example.BE_DATN.entity.Field;
import com.example.BE_DATN.service.FieldService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/field")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldController {
    @Autowired
    FieldService fieldService;

    @PostMapping
    ApiRespone<FieldRespone> createField(@ModelAttribute FieldRequest fieldRequest,
                                         @RequestParam("img")MultipartFile file) throws IOException {
        return ApiRespone.<FieldRespone>builder()
                .code(200)
                .message("Success")
                .result(fieldService.createField(fieldRequest,file))
                .build();
    }
    @GetMapping
    ApiRespone<List<FieldRespone>> getFields(){
        return ApiRespone.<List<FieldRespone>>builder()
                .code(200)
                .result(fieldService.getFields())
                .build();
    }
    @PutMapping("/{idField}")
    ApiRespone<FieldRespone> updateField(@PathVariable("idField") Long idField,
                                         @ModelAttribute FieldUpdate fieldUpdate, @RequestParam("img") MultipartFile file)
    throws IOException{
        return ApiRespone.<FieldRespone>builder()
                .code(200)
                .message("Success")
                .result(fieldService.update(idField, fieldUpdate,file))
                .build();
    }
    @GetMapping("/{idType}/{idStadium}")
    ApiRespone<List<Field>> getFieldByIdTypeAndIdStadium(@PathVariable("idType") Long idType,
                                                         @PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<Field>>builder()
                .code(200)
                .result(fieldService.getFieldsByIdType(idType,idStadium))
                .build();
    }

    @PutMapping("/{idField}/enable")
    ApiRespone<Field> updateEnable(@PathVariable("idField") Long idField){
        return ApiRespone.<Field>builder()
                .code(200)
                .result(fieldService.removeField(idField))
                .build();
    }

    @GetMapping("/{idType}/{idStadium}/Type11")
    ApiRespone<List<Field>> getFieldByIdType11(@PathVariable("idType") Long idType,
                                               @PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<Field>>builder()
                .code(200)
                .result(fieldService.getFiedlsByIdType2(idType,idStadium))
                .build();
    }

    @GetMapping("/{idStadium}/{idType}/enable")
    ApiRespone<List<FieldRespone>> getFieldByIdStadiumAndIdType(@PathVariable("idStadium") Long idStadium,
                                                                @PathVariable("idType") Long idType){
        return ApiRespone.<List<FieldRespone>>builder()
                .code(200)
                .message("Success")
                .result(fieldService.getFiedlsByIdStadiumAndIsTypeAndEnable(idStadium,idType))
                .build();
    }
    @GetMapping("/{idStadium}/ListType7")
    ApiRespone<List<Field>> getListFieldByIdTypeAndIdStadium(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<Field>>builder()
                .code(200)
                .message("Success")
                .result(fieldService.getFieldByIdTypeAndIdStadium(idStadium))
                .build();
    }

    @GetMapping("/{idStadium}/{idTime}/{day}")
    ApiRespone<List<FieldEmptyRespone>> getFieldEmpty(@PathVariable("idStadium") Long idStadium,
                                                      @PathVariable("idTime") Long idTime,
                                                      @PathVariable("day")LocalDate day){
        return ApiRespone.<List<FieldEmptyRespone>>builder()
                .code(200)
                .message("Success")
                .result(fieldService.getFieldEmpty(idStadium,day,idTime))
                .build();
    }
}
