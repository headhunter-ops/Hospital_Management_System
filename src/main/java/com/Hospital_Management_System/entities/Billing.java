package com.Hospital_Management_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal cost;

    private LocalDateTime billingDateTime;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
