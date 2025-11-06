package com.example.taskmanager.controller;

import com.example.taskmanager.dto.MedicationDetails;
import com.example.taskmanager.dto.TaskDetails;
import com.example.taskmanager.dto.User;
import com.example.taskmanager.mysql.entities.Task;
import com.example.taskmanager.services.AuthServices;
import com.example.taskmanager.services.MedicationServices;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createMedicationTask(@RequestBody MedicationDetails medicationDetails, @RequestHeader("Authorization") String token ) {
        medicationServices.addMedication(medicationDetails, token);
    }

    @GetMapping("/getMedications")
    public List<MedicationDetails> getMedicationlist( @RequestHeader("Authorization") String token){
        return medicationServices.getMedication(token);
    }

}
