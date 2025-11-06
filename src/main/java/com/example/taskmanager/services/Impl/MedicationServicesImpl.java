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
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
}
