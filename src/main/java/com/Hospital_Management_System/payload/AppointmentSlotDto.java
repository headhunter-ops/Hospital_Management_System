package com.Hospital_Management_System.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSlotDto {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long doctorId;
}
