package com.Hospital_Management_System.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceDto {

    private Long id;
    private String policyNumber;
    private BigDecimal coverageAmount;
    private Long patientId;
}
