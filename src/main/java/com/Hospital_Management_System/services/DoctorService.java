package com.Hospital_Management_System.services;

import com.Hospital_Management_System.payload.DoctorDto;
import com.Hospital_Management_System.utilities.DoctorResponse;
import com.Hospital_Management_System.utilities.PatientResponse;

public interface DoctorService {

    public DoctorDto createDoctor(DoctorDto doctorDto);
    public DoctorDto getDoctorById(Long id);
    public void deleteDoctorById(Long id);
    public DoctorDto updateDoctor(Long id, DoctorDto doctorDto);

    public DoctorResponse doctorList(int pageNo, int pageSize, String sortBy, String sortDir);

}
