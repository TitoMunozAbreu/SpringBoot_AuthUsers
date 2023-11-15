package com.example.authusers.services;

import com.example.authusers.models.*;
import com.example.authusers.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public boolean save(UserRequest userRequest) {
        boolean isUserCreate = false;

        boolean existsUserByEmail = this.userRepository.existsUserByEmail(userRequest.getEmail());

        if(!existsUserByEmail){
            User newUser = convertUserRequestToUser(userRequest);
            this.userRepository.save(newUser);
            System.out.println("usuario registrado");
            isUserCreate = true;
        }

        return isUserCreate;
    }

    @Override
    @Transactional
    public List<UserResponse> findAllUsers() {
        return convertListUsersToListUsersResponse(this.userRepository.findAll());
    }

    @Override
    @Transactional
    public String findUserRoleByEmail(String email) {
        return this.userRepository.findUserByEmail(email)
                .get()
                .getRoles()
                .stream()
                .findFirst()
                .get()
                .getName();
    }


    public User convertUserRequestToUser(UserRequest userRequest){
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .roles(userRequest.getRoles())
                .build();
    }

    public UserResponse convertUserToUserResponse(User user){
        return new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getRoles());
    }

    public List<UserResponse> convertListUsersToListUsersResponse(List<User> users){
        return users
                .stream()
                .map( user -> {
                    return convertUserToUserResponse(user);
                }).collect(Collectors.toList());
    }

    public boolean existsUserByEmailAndAndPassword(UserLoginRequest userLoginRequest) {
        return this.userRepository.existsUserByEmailAndAndPassword(
                userLoginRequest.getEmail(),
                userLoginRequest.getPassword());
    }
}
