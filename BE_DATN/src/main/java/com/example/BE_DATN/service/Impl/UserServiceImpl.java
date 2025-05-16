package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.Mapper.UserMapper;
import com.example.BE_DATN.dto.CurrentAccountDTO;
import com.example.BE_DATN.dto.request.UserRequest;
import com.example.BE_DATN.dto.request.UserUpdate;
import com.example.BE_DATN.dto.respone.UserRespone;
import com.example.BE_DATN.entity.Role;
import com.example.BE_DATN.entity.User;
import com.example.BE_DATN.enums.Enable;
import com.example.BE_DATN.enums.RoleEnums;
import com.example.BE_DATN.enums.StatusChangePwd;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.RoleRepository;
import com.example.BE_DATN.repository.UserRepository;
import com.example.BE_DATN.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserRespone createUser(UserRequest request) {
        if(userRepository.existsByName(request.getName())) {throw new AppException(ErrorCode.USER_EXISTED);}
        if(userRepository.existsByEmail(request.getEmail())) {throw new AppException(ErrorCode.EMAIL_EXISTED);}
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);}
        Role role = roleRepository.findByName(RoleEnums.USER.name())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        User user = userMapper.toUser(request);
        user.setIdRole(role.getIdRole());
        user.setEnable(Enable.ENABLE.name());
        user.setChangePassword(StatusChangePwd.FALSE.name());
        UserRespone user1 = userMapper.toUserRespone(userRepository.save(user));
        user1.setNameRole(role.getName());
        return user1;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserRespone> getUsers() {
        return userRepository.findAll().stream().map(user ->{
            UserRespone userRespone = userMapper.toUserRespone(user);
            Role role = roleRepository.findById(user.getIdRole())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
            userRespone.setNameRole(role.getName());
            userRespone.setIdUser(user.getIdUser());
            return userRespone;
        }).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void DeleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(id);
    }

    @Override
    public UserRespone getUser(Long id) {
        if(!Objects.equals(CurrentAccountDTO.getIdUser(), id)){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return userRepository.getUserByIdUser(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    public User updateUser(Long id, UserUpdate userUpdate) {
        if (!Objects.equals(CurrentAccountDTO.getIdUser(), id)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(userUpdate.getName() != null
                && !userUpdate.getName().isBlank()
                && !userUpdate.getName().equals(user.getName())){
            if(userRepository.existsByName(userUpdate.getName())){
                throw new AppException(ErrorCode.NAME_EXISTED);
            }
        }
        if(userUpdate.getPhoneNumber() != null
                && !userUpdate.getPhoneNumber().isBlank()
                && !userUpdate.getPhoneNumber().equals(user.getPhoneNumber())){
            if(userRepository.existsByPhoneNumber(userUpdate.getPhoneNumber())){
                throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
            }
        }
        if(userUpdate.getEmail() != null
                && !userUpdate.getEmail().isBlank()
                && !userUpdate.getEmail().equals(user.getEmail())){
            if(userRepository.existsByEmail(userUpdate.getEmail())){
                throw new AppException(ErrorCode.EMAIL_EXISTED);
            }
        }
        userMapper.updateUser(user,userUpdate);
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public User removeUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setEnable(Enable.UNENABLE.name());
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public User updateRole(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Role role = roleRepository.findById(user.getIdRole())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        String newRoleName = role.getName().equals(RoleEnums.ADMIN.name())
                ? RoleEnums.USER.name()
                : RoleEnums.ADMIN.name();
        Role newRole = roleRepository.findByName(newRoleName).orElseThrow(() ->new AppException(ErrorCode.NOT_FOUND));
        user.setIdRole(newRole.getIdRole());
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('USER')")
    @Override
    public User changePassword(Long idUser, String pwd, String newPwd) {
        if(!Objects.equals(CurrentAccountDTO.getIdUser(),idUser)){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        User user = userRepository.findByIdUserAndPassword(idUser,pwd)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setPassword(newPwd);
        return userRepository.save(user);
    }

}
