package com.example.taskmanager.services;

import com.example.taskmanager.dto.MedicationDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicationServices {

    ResponseEntity<?> addMedication(MedicationDetails medicationDetails, String token);
    List<MedicationDetails> getMedication(String token);
    ResponseEntity<?> deleteMedication(Integer id, String token);
    ResponseEntity<?> editMedication(Integer id, MedicationDetails updatedDetails, String token);

}
