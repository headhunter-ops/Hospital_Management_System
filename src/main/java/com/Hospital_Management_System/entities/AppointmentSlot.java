package com.Hospital_Management_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
