package com.Hospital_Management_System.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be a 10-digit number")
    private String phoneNumber;

    @Email(message = "Invalid email address")
    private String email;

    @Min(value = 0, message = "Consultation fee must be a non-negative value")
    private int consultationFee;

    private List<AppointmentDto> appointments;

    }
