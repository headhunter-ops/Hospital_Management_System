package com.Hospital_Management_System.repositories;

import com.Hospital_Management_System.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
