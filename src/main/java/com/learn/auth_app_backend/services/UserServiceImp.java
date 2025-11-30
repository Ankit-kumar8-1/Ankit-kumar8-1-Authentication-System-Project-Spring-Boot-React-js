package com.learn.auth_app_backend.services;

import com.learn.auth_app_backend.dtos.UserDto;
import com.learn.auth_app_backend.entities.Provider;
import com.learn.auth_app_backend.entities.User;
import com.learn.auth_app_backend.repositories.UserRepositiory;
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
//        check email empty ya null to nahi
        if(userDto.getEmail() == null || userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email is required");
        }

//        check kar rahe ke email phele se database m exit to nahi
        if(userRepositiory.existsByEmail(userDto.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }

//        yha hem mne  Dto ke data ko entity m convert kiya
        User user = modelMapper.map(userDto, User.class);
        user.setProvider(userDto.getProvider() != null ? userDto.getProvider() : Provider.LOCAL);

//        OR yha per entity ko DTO m kyoki return type DTO hai
        User savedUser = userRepositiory.save(user);
        return modelMapper.map(savedUser,UserDto.class);
    }




    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {

//        check kar lea ke email blank ya null to nahi
        if(email == null  || email.isBlank()){
            throw  new IllegalArgumentException("Email is Required ! ");
        }

//        user  get kiya from the database
        User user = userRepositiory.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("User not found"));

//         return kar dea user ko Dto m convert kar ke
        return modelMapper.map(user,UserDto.class);
    }



    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, String userId) {

//        user ko databases se lena , ager nahi mila to exception throw karna
        User user =  userRepositiory.findById(UUID.fromString(userId))
                .orElseThrow(()-> new IllegalArgumentException("User not found"));

//        update Information
        user.setName(userDto.getName());
        user.setEnable(userDto.isEnable());
        user.setImage(userDto.getImage());
        user.setUpdatedAt(Instant.now());

//        user  ko save karna
        User updateUser = userRepositiory.save(user);

//        or fir return karna Dto m
        return modelMapper.map(user,UserDto.class);
    }




    @Override
    public void deleteUser(String userId) {

//        input m string id ko actual uuid m convert kiya
        UUID id = UUID.fromString(userId);

//        database se id hai ya nahi check kiya
        userRepositiory.findById(id)
                .orElseThrow(()->new IllegalArgumentException("User Not found"));

//        user ko remove kiya through ID
        userRepositiory.deleteById(id);

//        proper response de dea
        System.out.println("Remove Successful");
    }



    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(String userId) {
        User user =  userRepositiory.findById(UUID.fromString(userId))
                .orElseThrow(()->new IllegalArgumentException("User not Found !"));

        return modelMapper.map(user,UserDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<UserDto> getAllUsers() {
        return userRepositiory.findAll().stream().map(user -> modelMapper.map(user,UserDto.class)).toList();
    }
}
