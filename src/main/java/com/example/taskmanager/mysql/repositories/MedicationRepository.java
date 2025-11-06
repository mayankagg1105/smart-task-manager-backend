package com.example.taskmanager.mysql.repositories;

import com.example.taskmanager.mysql.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query("SELECT m FROM Medication m WHERE m.userid = :id")
    List<Medication> findById(@Param("id") int id);

}
