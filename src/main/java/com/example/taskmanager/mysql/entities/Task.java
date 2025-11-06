package com.example.taskmanager.mysql.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "task")
    private String task;

    @Column(name = "duedatetime")
    private LocalDateTime duedatetime; // stores both date + time

    @Column(name = "userid")
    private Integer userid;

    @Column(name = "priority")
    private String priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}


