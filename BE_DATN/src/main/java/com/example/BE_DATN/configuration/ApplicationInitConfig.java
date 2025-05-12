package com.example.BE_DATN.configuration;

import com.example.BE_DATN.entity.User;
import com.example.BE_DATN.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args ->{
            if(userRepository.findADMIN().isEmpty()){
                User user = User.builder()
                        .name("ADMIN")
                        .password("ADMIN")
                        .idRole(2L)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
