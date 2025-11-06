package com.example.taskmanager.dto;

import jakarta.persistence.Column;

import java.time.LocalTime;
import java.util.List;

public class MedicationDetails {

    private String name;
    private String dosage;
    private String frequency;
    private String notes;
    private int times;
    private List<LocalTime> localtimeList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public List<LocalTime> getLocaltimeList() {
        return localtimeList;
    }

    public void setLocaltimeList(List<LocalTime> localtimeList) {
        this.localtimeList = localtimeList;
    }
}
