package com.Hospital_Management_System.repositories;

import com.Hospital_Management_System.entities.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot,Long> {
}
