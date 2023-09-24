package com.Hospital_Management_System.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingDto {

    private Long id;
    private BigDecimal cost;
    private LocalDateTime billingDateTime;
    private Long patientId;  //
}
