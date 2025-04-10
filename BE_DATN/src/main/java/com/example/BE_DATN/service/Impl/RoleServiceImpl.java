package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.Mapper.RoleMapper;
import com.example.BE_DATN.dto.request.RoleRequest;
import com.example.BE_DATN.dto.respone.RoleRespone;
import com.example.BE_DATN.entity.Role;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.RoleRepository;
import com.example.BE_DATN.service.RoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleMapper roleMapper;
    @Override
    public RoleRespone createRole(RoleRequest request) {
        if(roleRepository.existsByName(request.getName())) throw new AppException(ErrorCode.ROLE_EXISTED);
        Role role = roleMapper.toRole(request);
        return roleMapper.toRoleRespone(role);
    }

    @Override
    public List<RoleRespone> getRoles() {
        return roleRepository.findAll().stream().map(
                role -> roleMapper.toRoleRespone(role)).toList();
    }

    @Override
    public void DeleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }
}
