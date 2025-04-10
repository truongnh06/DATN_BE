package com.example.BE_DATN.Mapper;

import com.example.BE_DATN.dto.request.RoleRequest;
import com.example.BE_DATN.dto.respone.RoleRespone;
import com.example.BE_DATN.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);
    RoleRespone toRoleRespone(Role role);
}
