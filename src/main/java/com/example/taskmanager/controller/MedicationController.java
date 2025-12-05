package com.example.taskmanager.controller;

import com.example.taskmanager.dto.EditTaskdto;
import com.example.taskmanager.dto.MedicationDetails;
import com.example.taskmanager.dto.TaskDetails;
import com.example.taskmanager.dto.User;
import com.example.taskmanager.mysql.entities.Task;
import com.example.taskmanager.services.AuthServices;
import com.example.taskmanager.services.MedicationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/medication")
@CrossOrigin(origins = "*")
@RestController
public class MedicationController {

    @Autowired
    MedicationServices medicationServices;

    @Autowired
    AuthServices authServices;

    @PostMapping("/addMedication")
    public ResponseEntity<?> createMedicationTask(@RequestBody MedicationDetails medicationDetails, @RequestHeader("Authorization") String token ) {
        return medicationServices.addMedication(medicationDetails, token);
    }

    @GetMapping("/getMedications")
    public List<MedicationDetails> getMedicationlist( @RequestHeader("Authorization") String token){
        return medicationServices.getMedication(token);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMedication(@RequestHeader("Authorization") String token,
                                              @PathVariable Integer id) {
        return medicationServices.deleteMedication(id, token);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editMedication(@RequestHeader("Authorization") String token,
                                            @PathVariable Integer id,
                                            @RequestBody MedicationDetails updatedDetails) {
        return medicationServices.editMedication(id, updatedDetails, token);
    }


}
