package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.request.RoleRequest;
import com.example.BE_DATN.dto.respone.RoleRespone;

import java.util.List;

public interface RoleService {
    public RoleRespone createRole(RoleRequest request);
    public List<RoleRespone> getRoles();
    public void DeleteRole(Long roleId);
}
