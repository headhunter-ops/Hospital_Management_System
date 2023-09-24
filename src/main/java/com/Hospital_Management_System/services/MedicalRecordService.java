package com.Hospital_Management_System.services;

import com.Hospital_Management_System.payload.MedicalRecordDto;
import com.Hospital_Management_System.utilities.MedicalRecordResponse;

public interface MedicalRecordService {

    public MedicalRecordDto createMedicalRecordWithPatientId(MedicalRecordDto medicalRecordDto);
    public MedicalRecordDto getRecordById(Long id);
    public MedicalRecordDto updateRecordById(MedicalRecordDto medicalRecordDto, Long id);
    public void deleteRecordById(Long id);
    public MedicalRecordResponse medicalRecordList(int pageNo, int pageSize, String sortBy, String sortDir);

}
