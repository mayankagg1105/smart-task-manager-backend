package com.example.taskmanager.mysql.repositories;

import com.example.taskmanager.dto.LoginDetails;
import com.example.taskmanager.mysql.entities.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    // you can add custom query methods here if needed
//    @Query("SELECT * FROM AUTH WHERE EMAIL = EMAIL")
//    Optional<Auth> findById(String email);

    @Query(value = "SELECT * FROM AUTH WHERE EMAIL = :email", nativeQuery = true)
    Optional<Auth> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM AUTH WHERE TOKEN = :token", nativeQuery = true )
    Optional<Auth> findByToken(@Param("token") String token);
}
