package com.example.taskmanager.dto;

import java.time.LocalDateTime;

public class EditTaskdto {

    private String newTask;
    private LocalDateTime newDuedatetime;
    private String newPriority;

    public String getNewTask() {
        return newTask;
    }

    public void setNewTask(String newTask) {
        this.newTask = newTask;
    }

    public LocalDateTime getNewDuedatetime() {
        return newDuedatetime;
    }

    public void setNewDuedatetime(LocalDateTime newDuedatetime) {
        this.newDuedatetime = newDuedatetime;
    }

    public String getNewPriority() {
        return newPriority;
    }

    public void setNewPriority(String newPriority) {
        this.newPriority = newPriority;
    }
}
