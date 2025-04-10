package com.example.BE_DATN.Mapper;

import com.example.BE_DATN.dto.request.UserRequest;
import com.example.BE_DATN.dto.request.UserUpdate;
import com.example.BE_DATN.dto.respone.UserRespone;
import com.example.BE_DATN.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest request);
    UserRespone toUserRespone(User user);

    void updateUser(@MappingTarget User user, UserUpdate userUpdate);
}
