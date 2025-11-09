package com.example.taskmanager.services;

import com.example.taskmanager.dto.EditTaskdto;
import com.example.taskmanager.dto.TaskDetails;
import com.example.taskmanager.dto.User;
import com.example.taskmanager.mysql.entities.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskServices {
    void createTask(TaskDetails taskdetails, User user);
    List<Task> getTasks(User user);
    ResponseEntity<?> deleteTask(Integer id, String token);
    ResponseEntity<?> editTask(Integer id, EditTaskdto editTaskdto, String token);

}



