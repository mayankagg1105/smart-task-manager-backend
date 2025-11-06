package com.example.taskmanager.mysql.repositories;

import com.example.taskmanager.mysql.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT * FROM task WHERE userid = :userid", nativeQuery = true)
    List<Task> findByUserId(@Param("userid") Integer userId);
}


