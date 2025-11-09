package com.example.taskmanager.services.Impl;

import com.example.taskmanager.dto.MedicationDetails;
import com.example.taskmanager.dto.User;
import com.example.taskmanager.mysql.entities.Medication;
import com.example.taskmanager.mysql.entities.MedicineTimes;
import com.example.taskmanager.mysql.repositories.AuthRepository;
import com.example.taskmanager.mysql.repositories.MedicationRepository;
import com.example.taskmanager.mysql.repositories.MedicineTimesRepository;
import com.example.taskmanager.services.AuthServices;
import com.example.taskmanager.services.MedicationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MedicationServicesImpl implements MedicationServices {

    @Autowired
    AuthServices authServices;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    MedicineTimesRepository medicineTimesRepository;

    @Override
    public void addMedication(MedicationDetails medicationDetails, String token){

        User user = authServices.verifyToken(token);
        Medication medication = new Medication();
        medication.setName(medicationDetails.getName());
        medication.setDosage(medicationDetails.getDosage());
        medication.setFrequency(medicationDetails.getFrequency());
        medication.setNotes(medicationDetails.getNotes());
        medication.setTimes(medicationDetails.getTimes());
        medication.setUserid(user.getUserId());
        medicationRepository.save(medication);

        int generatedId = medication.getId();

        for(LocalTime time : medicationDetails.getLocaltimeList()){
            MedicineTimes medicineTimes = new MedicineTimes();
            medicineTimes.setMedicationid(generatedId);
            medicineTimes.setTime(time);
            medicineTimesRepository.save(medicineTimes);
        }

    }

    @Override
    public List<MedicationDetails> getMedication(String token){
        User user = authServices.verifyToken(token);
        List<Medication> medications = medicationRepository.findById(user.getUserId());
        List<MedicationDetails> medicationDetailslist = new ArrayList<>();
        for(Medication medication : medications){
            MedicationDetails medicationDetails = new MedicationDetails();
            medicationDetails.setDosage(medication.getDosage());
            medicationDetails.setFrequency(medication.getFrequency());
            medicationDetails.setName(medication.getName());
            medicationDetails.setTimes(medication.getTimes());
            medicationDetails.setNotes(medication.getNotes());
            List<LocalTime> medicineTimes = medicineTimesRepository.findById(medication.getId());
            medicationDetails.setLocaltimeList(medicineTimes);
            medicationDetailslist.add(medicationDetails);
        }
        return medicationDetailslist;
    }

    @Override
    public ResponseEntity<?> deleteMedication(Integer id, String token) {
        User user = authServices.verifyToken(token);
        Optional<Medication> medicationOpt = medicationRepository.findById(Long.valueOf(id));

        if (medicationOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Medication not found!");
        }

        Medication medication = medicationOpt.get();
        if (!Objects.equals(medication.getUserid(), user.getUserId())) {
            return ResponseEntity.status(403).body("Access denied!");
        }

        // First delete related medicine times
        List<MedicineTimes> times = medicineTimesRepository.findAllByMedicationid(id);
        medicineTimesRepository.deleteAll(times);

        // Then delete the medication
        medicationRepository.delete(medication);

        return ResponseEntity.ok("Medication deleted successfully!");
    }

    @Override
    public ResponseEntity<?> editMedication(Integer id, MedicationDetails updatedDetails, String token) {
        User user = authServices.verifyToken(token);
        Optional<Medication> medicationOpt = medicationRepository.findById(Long.valueOf(id));

        if (medicationOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Medication not found!");
        }

        Medication medication = medicationOpt.get();
        if (!Objects.equals(medication.getUserid(), user.getUserId())) {
            return ResponseEntity.status(403).body("Access denied!");
        }

        // Update medication fields
        medication.setName(updatedDetails.getName());
        medication.setDosage(updatedDetails.getDosage());
        medication.setFrequency(updatedDetails.getFrequency());
        medication.setNotes(updatedDetails.getNotes());
        medication.setTimes(updatedDetails.getTimes());
        medicationRepository.save(medication);

        // Delete old medicine times and insert new ones
        List<MedicineTimes> oldTimes = medicineTimesRepository.findAllByMedicationid(id);
        medicineTimesRepository.deleteAll(oldTimes);

        for (LocalTime time : updatedDetails.getLocaltimeList()) {
            MedicineTimes newTime = new MedicineTimes();
            newTime.setMedicationid(id);
            newTime.setTime(time);
            medicineTimesRepository.save(newTime);
        }

        return ResponseEntity.ok("Medication updated successfully!");
    }

}
