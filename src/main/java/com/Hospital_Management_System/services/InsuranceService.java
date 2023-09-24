package com.Hospital_Management_System.services;

import com.Hospital_Management_System.payload.InsuranceDto;
import com.Hospital_Management_System.utilities.InsuranceResponse;
import com.Hospital_Management_System.utilities.MedicalRecordResponse;

public interface InsuranceService {

    public InsuranceDto createInsurance(InsuranceDto insuranceDto);
    public InsuranceDto getInsuranceById(Long id);
    public InsuranceDto updateInsurance(InsuranceDto insuranceDto,Long id);
    public void deleteInsurance(Long id);

    public InsuranceResponse insuranceList(int pageNo, int pageSize, String sortBy, String sortDir);
}
