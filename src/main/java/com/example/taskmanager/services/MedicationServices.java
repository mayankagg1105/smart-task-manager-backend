package com.example.taskmanager.services;

import com.example.taskmanager.dto.MedicationDetails;

import java.util.List;

public interface MedicationServices {

    void addMedication(MedicationDetails medicationDetails, String token);
    List<MedicationDetails> getMedication(String token);

}
