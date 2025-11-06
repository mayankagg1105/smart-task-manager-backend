package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/auth")
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "welcome to java backend server";
    }
}
