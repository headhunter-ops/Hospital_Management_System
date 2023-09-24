package com.Hospital_Management_System.repositories;

import com.Hospital_Management_System.entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long> {
}
