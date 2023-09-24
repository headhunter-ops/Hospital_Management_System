package com.Hospital_Management_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="Patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Date of Birth is required")
    @Past(message = "Date of Birth must be in the past")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @NotBlank(message = "Address is required")
    private String address;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be a 10-digit number")
    private String phoneNumber;

    @Email(message = "Invalid email address")
    private String email;

    private String medicalHistory;

    @NotBlank(message = "Insurance Policy Number is required")
    private String insurancePolicyNumber;

    @Pattern(regexp = "\\d{10}", message = "Emergency contact phone number must be a 10-digit number")
    private String emergencyContactPhone;

    @OneToMany(mappedBy = "patient")
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    private List<Billing> billings = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    private List<Insurance> insurances = new ArrayList<>();

}
