package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.entity.User;
import com.example.BE_DATN.enums.StatusChangePwd;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.UserRepository;
import com.example.BE_DATN.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    UserRepository userRepository;
    @Override
    public void sendNewPassword(String email) {
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
        String newPwd = generateRandomPassword(10);
        user.setChangePassword(StatusChangePwd.TRUE.name());
        user.setPassword(newPwd);
        userRepository.save(user);

        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("hatruong06012003@gmail.com");
            helper.setTo(email);
            helper.setSubject("New Password");
            helper.setText("New Password:" + newPwd);
            javaMailSender.send(message);
        } catch (MessagingException e){
            e.printStackTrace();
        }
    }

    private String generateRandomPassword(int length){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
