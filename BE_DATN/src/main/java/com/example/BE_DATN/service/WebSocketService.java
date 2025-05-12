package com.example.BE_DATN.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebSocketService {
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    public void sendNotificationToUser(Long idUser, String message){
        messagingTemplate.convertAndSend("/topic/user/" + idUser, message);
    }
}
