package com.example.taskmanager.services;

import com.example.taskmanager.dto.TaskDetails;
import com.example.taskmanager.dto.User;
import com.example.taskmanager.mysql.entities.Task;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskServices {
    void createTask(TaskDetails taskdetails, User user);
    List<Task> getTasks(User user);
}



