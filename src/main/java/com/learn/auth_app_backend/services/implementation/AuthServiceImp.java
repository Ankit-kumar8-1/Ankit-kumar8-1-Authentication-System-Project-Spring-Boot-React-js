package com.learn.auth_app_backend.services.implementation;

import com.learn.auth_app_backend.dtos.UserDto;
import com.learn.auth_app_backend.services.AuthService;
import com.learn.auth_app_backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserService userService;
    @Override
    public UserDto register(UserDto userDto) {
        UserDto userDto1 = userService.createUser(userDto);
        return userDto1;
    }
}
