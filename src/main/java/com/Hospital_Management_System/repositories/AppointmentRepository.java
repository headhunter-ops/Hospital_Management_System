package com.Hospital_Management_System.repositories;

import com.Hospital_Management_System.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
