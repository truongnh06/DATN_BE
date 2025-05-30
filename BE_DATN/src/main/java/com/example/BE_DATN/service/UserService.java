package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.UserRequest;
import com.example.BE_DATN.dto.request.UserUpdate;
import com.example.BE_DATN.dto.respone.UserRespone;
import com.example.BE_DATN.entity.User;

import java.util.List;

public interface UserService {
    public UserRespone createUser(UserRequest request);
    public List<UserRespone> getUsers();
    public void DeleteUser(Long id);
    public UserRespone getUser(Long id);
    public User updateUser(Long id, UserUpdate userUpdate);
    public User removeUser(Long id);
    public User updateRole(Long id);
    public User changePassword(Long idUser, String pwd, String newPwd);
}
