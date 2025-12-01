package com.learn.auth_app_backend.services;

import com.learn.auth_app_backend.dtos.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


public interface UserService {

    UserDto createUser(UserDto userDto) ;

    UserDto getUserByEmail(String email);

    UserDto updateUser(UUID id, UserDto userDto);

    void  deleteUserById(UUID userId);

    UserDto getUserById(UUID userId);

    Iterable<UserDto> getAllUsers();
}
