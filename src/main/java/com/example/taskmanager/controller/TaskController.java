package com.example.taskmanager.controller;
import com.example.taskmanager.dto.TaskDetails;
import com.example.taskmanager.dto.User;
import com.example.taskmanager.mysql.entities.Task;
import com.example.taskmanager.services.AuthServices;
import com.example.taskmanager.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*") // allow frontend to access
public class TaskController {

    @Autowired
    TaskServices taskServices;

    @Autowired
    AuthServices authServices;

    @PostMapping("/add")
    public void createTask(@RequestBody TaskDetails taskDetails, @RequestHeader("Authorization") String token ) {
        User user = authServices.verifyToken(token);
        taskServices.createTask(taskDetails, user);
    }

    @GetMapping("/getTasks")
    public List<Task> getTasks(@RequestHeader("Authorization") String token){
        User user = authServices.verifyToken(token);
        return taskServices.getTasks(user);
    }
}





