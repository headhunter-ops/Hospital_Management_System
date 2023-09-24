package com.Hospital_Management_System.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {

    private Long id;
    private LocalDateTime appointmentDateTime;
    private Long patientId;
    private Long doctorId;
}
