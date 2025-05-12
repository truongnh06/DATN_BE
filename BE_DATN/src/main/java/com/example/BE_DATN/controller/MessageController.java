package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.entity.Message;
import com.example.BE_DATN.service.MessageService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping("/{idUser}")
    public ApiRespone<List<Message>> getMessageNotYetRead(@PathVariable("idUser") Long idUser){
        return ApiRespone.<List<Message>>builder()
                .code(200)
                .message("Success")
                .result(messageService.getMessageByIdUseNotYetRead(idUser))
                .build();
    }

    @PutMapping("/{idMessage}")
    public ApiRespone<Message> updateRead(@PathVariable("idMessage") Long idMessage){
        return ApiRespone.<Message>builder()
                .code(200)
                .message("Success")
                .result(messageService.updateRead(idMessage))
                .build();
    }
}
