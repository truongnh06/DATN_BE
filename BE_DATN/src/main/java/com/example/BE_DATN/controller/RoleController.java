package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.RoleRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.RoleRespone;
import com.example.BE_DATN.service.RoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping
    ApiRespone<RoleRespone> createRole(@RequestBody RoleRequest request){

        return ApiRespone.<RoleRespone>builder()
                .result(roleService.createRole(request))
                .code(1000)
                .message("Success")
                .build();
    }

    @GetMapping
    ApiRespone<List<RoleRespone>> getRole(){
        return ApiRespone.<List<RoleRespone>>builder()
                .result(roleService.getRoles())
                .build();
    }

    @DeleteMapping("/{roleId}")
    String deleteRole(@PathVariable Long roleId){
        roleService.DeleteRole(roleId);
        return "Role deleted";
    }
}
