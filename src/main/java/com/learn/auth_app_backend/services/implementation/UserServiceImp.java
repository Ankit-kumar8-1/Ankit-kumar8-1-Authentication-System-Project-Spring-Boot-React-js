package com.learn.auth_app_backend.services.implementation;

import com.learn.auth_app_backend.dtos.UserDto;
import com.learn.auth_app_backend.entities.Provider;
import com.learn.auth_app_backend.entities.User;
import com.learn.auth_app_backend.exception.ResourceNotFoundException;
import com.learn.auth_app_backend.repositories.UserRepositiory;
import com.learn.auth_app_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepositiory userRepositiory;
    private  final ModelMapper modelMapper;



    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {

        if (userRepositiory.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = modelMapper.map(userDto, User.class);

        user.setProvider(
                userDto.getProvider() != null
                        ? userDto.getProvider()
                        : Provider.LOCAL
        );

        User savedUser = userRepositiory.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }



    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {

        User user = userRepositiory.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email:  " + email));

        return modelMapper.map(user,UserDto.class);
    }


    @Transactional
    @Override
    public UserDto updateUser(UUID id, UserDto userDto) {

        // 1. Old user lo
        User user = userRepositiory.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 2. Only updatable fields change karo
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }

        if (userDto.getImage() != null) {
            user.setImage(userDto.getImage());
        }

        user.setEnable(userDto.isEnable());
        user.setUpdatedAt(Instant.now());
        User updatedUser = userRepositiory.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }


    @Override
    public void deleteUserById(UUID userId) {

        UUID id = userId;

        userRepositiory.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User Not found"));

        System.out.println("Remove successful");
        userRepositiory.deleteById(id);
    }



    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(UUID userId) {
        User user =  userRepositiory.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not Found !"));

        return modelMapper.map(user,UserDto.class);
    }


    @Override
    @Transactional(readOnly = true)
    public Iterable<UserDto> getAllUsers() {
        return userRepositiory.findAll().stream().map(user -> modelMapper.map(user,UserDto.class)).toList();
    }
}
