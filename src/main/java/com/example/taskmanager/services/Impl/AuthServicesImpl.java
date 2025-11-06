package com.example.taskmanager.services.Impl;

import com.example.taskmanager.dto.*;
import com.example.taskmanager.mysql.entities.Auth;
import com.example.taskmanager.mysql.repositories.AuthRepository;
import com.example.taskmanager.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServicesImpl implements AuthServices {

    @Autowired
    AuthRepository authRepository;

    @Override
    public void registerUser(SignupDetails signupDetails) {
        Auth auth = new Auth();
        auth.setName(signupDetails.getUsername());
        auth.setEmail(signupDetails.getEmail());
        auth.setPassword(signupDetails.getPassword());
        authRepository.save(auth);
    }

    @Override
    public LoginResponse loginUser(com.example.taskmanager.dto.LoginDetails loginDetails) throws Exception {
        Optional<Auth> auth = authRepository.findByEmail(loginDetails.getEmail());
        com.example.taskmanager.dto.LoginResponse loginResponse = new com.example.taskmanager.dto.LoginResponse();
        if (auth.isEmpty()) {
            loginResponse.setMessage("user doesn't exist");
            return loginResponse;
        }
        Auth foundAuth = auth.get();

        if (!Objects.equals(loginDetails.getPassword(), foundAuth.getPassword())) {
            loginResponse.setMessage("password id incorrect");
            return loginResponse;
        }

        Random random = new Random();
        String token = String.format("%05d", random.nextInt(100000));
        foundAuth.setToken(token);
        authRepository.save(foundAuth);

        loginResponse.setToken(token);
        loginResponse.setMessage("login successfully");

        return loginResponse;
    }

    @Override
    public ResponseEntity<com.example.taskmanager.dto.UserResponse> getUser(String token) {
        Optional<Auth> auth = authRepository.findByToken(token);
        com.example.taskmanager.dto.UserResponse userResponse = new com.example.taskmanager.dto.UserResponse();

        if (auth.isEmpty()) {
            userResponse.setMessage("wrong token");
            return new ResponseEntity<>(userResponse, HttpStatus.FORBIDDEN);
        }

        userResponse.setUsername(auth.get().getName());
        userResponse.setMessage("user is logged in");
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @Override
    public User verifyToken(String token){
        Optional<Auth> auth = authRepository.findByToken(token);
        User user = new User();
        if(auth.isEmpty()){
            return user;
        }
        user.setUserId(auth.get().getId());
        return user;
    }

}