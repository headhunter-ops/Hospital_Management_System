package com.Hospital_Management_System.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDto {

    private Long id;
    private String diagnosis;
    private String prescriptions;
    private String testResults;
    private Long patientId;  //
}
