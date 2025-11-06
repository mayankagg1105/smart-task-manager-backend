package com.example.taskmanager.mysql.entities;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name="medicine_times")
public class MedicineTimes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="time")
    private LocalTime time;

    @Column(name="medicationid")
    private int medicationid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getMedicationid() {
        return medicationid;
    }

    public void setMedicationid(int medicationid) {
        this.medicationid = medicationid;
    }
}
