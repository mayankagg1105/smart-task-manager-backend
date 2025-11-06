package com.example.taskmanager.services;

import com.example.taskmanager.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthServices {
    void registerUser(SignupDetails signupDetails);
    com.example.taskmanager.dto.LoginResponse loginUser(com.example.taskmanager.dto.LoginDetails loginDetails) throws Exception;
    ResponseEntity<com.example.taskmanager.dto.UserResponse> getUser(String token);
    User verifyToken(String token);
    
}
