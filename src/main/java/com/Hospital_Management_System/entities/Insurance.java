package com.Hospital_Management_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyNumber;

    private BigDecimal coverageAmount;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

}