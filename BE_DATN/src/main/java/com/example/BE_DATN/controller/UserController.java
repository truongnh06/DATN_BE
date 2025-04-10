package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.UserRequest;
import com.example.BE_DATN.dto.request.UserUpdate;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.UserRespone;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    ApiRespone<UserRespone> createUser(@ModelAttribute @Valid UserRequest request){
        return ApiRespone.<UserRespone>builder()
                .result(userService.createUser(request))
                .code(1000)
                .message("Success")
                .build();
    }

    @GetMapping
    ApiRespone<List<UserRespone>> getUsers(){
        return ApiRespone.<List<UserRespone>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{idUser}")
    ApiRespone<UserRespone> getUser(@PathVariable("idUser") Long id_user){
        return ApiRespone.<UserRespone>builder()
                .result(userService.getUser(id_user))
                .build();
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id_user){
        try{
            userService.DeleteUser(id_user);
            return ResponseEntity.ok("Deleted Success");
        } catch (AppException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PutMapping("/{idUser}")
    UserRespone updateUser(@PathVariable("idUser") Long id_user, @ModelAttribute UserUpdate userUpdate){
        return userService.updateUser(id_user,userUpdate);
    }
}
