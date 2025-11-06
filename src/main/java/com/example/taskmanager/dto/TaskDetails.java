package com.example.taskmanager.dto;

import java.time.LocalDateTime;

public class TaskDetails {

    private String task;
    private LocalDateTime duedatetime;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalDateTime getDuedatetime() {
        return duedatetime;
    }

    public void setDuedatetime(LocalDateTime duedatetime) {
        this.duedatetime = duedatetime;
    }
}
