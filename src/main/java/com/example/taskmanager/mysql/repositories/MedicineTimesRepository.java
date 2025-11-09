package com.example.taskmanager.mysql.repositories;

import com.example.taskmanager.mysql.entities.Medication;
import com.example.taskmanager.mysql.entities.MedicineTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface MedicineTimesRepository extends JpaRepository<MedicineTimes, Long> {

    @Query("SELECT m.time FROM MedicineTimes m WHERE m.medicationid = :id")
    List<LocalTime> findById(@Param("id") int id);

    @Query("SELECT m FROM MedicineTimes m WHERE m.medicationid = :id")
    List<MedicineTimes> findAllByMedicationid(@Param("id") int id);

}
