package com.learn.auth_app_backend.controller;

import com.learn.auth_app_backend.dtos.UserDto;
import com.learn.auth_app_backend.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    private  final  UserService userService;

//    Create user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }


//    get all users
    @GetMapping
    public  ResponseEntity<Iterable<UserDto>> getAllUaers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
