package com.example.taskmanager.controller;
import com.example.taskmanager.dto.*;
import com.example.taskmanager.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthServices taskmanagerServicesImpl;

    @PostMapping("/signup")
    public String signup(@RequestBody com.example.taskmanager.dto.SignupDetails signupDetails) {
        taskmanagerServicesImpl.registerUser(signupDetails);
        return "register successfully";
    }

    @PostMapping("/login")
    public com.example.taskmanager.dto.LoginResponse login(@RequestBody com.example.taskmanager.dto.LoginDetails loginDetails) throws Exception {
        com.example.taskmanager.dto.LoginResponse loginResponse = taskmanagerServicesImpl.loginUser(loginDetails);
        return loginResponse;

    }

    @GetMapping("/user")
    public ResponseEntity<com.example.taskmanager.dto.UserResponse> getUser(@RequestHeader("Authorization") String token){
        // The common convention is to pass the token as "Bearer [token]".
        // We typically strip the "Bearer " prefix before passing it to the service.
        System.out.println(token);
        return taskmanagerServicesImpl.getUser(token);
    }

}

