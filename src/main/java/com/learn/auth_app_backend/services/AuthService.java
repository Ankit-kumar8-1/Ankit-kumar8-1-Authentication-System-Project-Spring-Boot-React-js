package com.learn.auth_app_backend.services;

import com.learn.auth_app_backend.dtos.UserDto;

public interface AuthService {

    UserDto register(UserDto userDto);
}
