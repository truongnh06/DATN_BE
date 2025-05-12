package com.example.BE_DATN.service;

import com.example.BE_DATN.entity.Message;

import java.time.LocalDate;
import java.util.List;

public interface MessageService {
    public Message createMessage(Long idUser, String message, LocalDate day, Long idMatching);
    public List<Message> getMessageByIdUseNotYetRead(Long idUser);
    public Message updateRead(Long idMessage);
}
