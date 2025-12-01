package com.learn.auth_app_backend.controller;

import com.learn.auth_app_backend.dtos.UserDto;
import com.learn.auth_app_backend.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    private  final  UserService userService;

//    Create user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }


//    get all users
    @GetMapping
    public  ResponseEntity<Iterable<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(  @PathVariable @NotBlank(message = "Email is required") @Email(message = "Email format is invalid") String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("{userid}")
    public  ResponseEntity<UserDto> getUserById(@PathVariable  UUID userid){
        return ResponseEntity.ok(userService.getUserById(userid));
    }

    @PatchMapping("{id}")
    public  ResponseEntity<UserDto> updateUserPartially(@PathVariable UUID id ,@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(id,userDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserById (@PathVariable  UUID id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("Remove successfull , ID : " + id);
    }


}
