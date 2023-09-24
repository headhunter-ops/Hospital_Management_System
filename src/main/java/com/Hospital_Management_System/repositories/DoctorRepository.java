package com.Hospital_Management_System.repositories;

import com.Hospital_Management_System.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
