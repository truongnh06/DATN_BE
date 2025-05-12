package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.entity.Message;
import com.example.BE_DATN.enums.StatusRead;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.MessageRepository;
import com.example.BE_DATN.service.MessageService;
import com.example.BE_DATN.service.WebSocketService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    WebSocketService webSocketService;
    @Override
    public Message createMessage(Long idUser, String message, LocalDate day, Long idMatching) {
        Message mg = Message.builder()
                .idUser(idUser)
                .message(message)
                .idMatching(idMatching)
                .day(day)
                .isRead(StatusRead.NO.name())
                .build();
        return messageRepository.save(mg);
    }

    @Override
    public List<Message> getMessageByIdUseNotYetRead(Long idUser) {
        List<Message> listMessage = messageRepository.getListMessageNotYetRead(idUser);
        if(listMessage != null && !listMessage.isEmpty()){
            webSocketService.sendNotificationToUser(idUser, "You have new notifications");
        }
        return listMessage;
    }

    @Override
    public Message updateRead(Long idMessage) {
        Message message = messageRepository.findById(idMessage)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        message.setIsRead(StatusRead.YES.name());
        return messageRepository.save(message);
    }
}
