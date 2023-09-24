package com.Hospital_Management_System.services;

import com.Hospital_Management_System.payload.PatientDto;
import com.Hospital_Management_System.utilities.PatientResponse;

public interface PatientService {

    public PatientDto createPatient(PatientDto patientDto);
    public PatientDto getPatientById(Long id);
    public void deletePatient(Long id);
    public PatientResponse patientList(int pageNo, int pageSize, String sortBy, String sortDir);
    public PatientDto updatePatient(Long id, PatientDto patientDto);
}
