package com.Hospital_Management_System.services.impl;

import com.Hospital_Management_System.entities.Appointment;
import com.Hospital_Management_System.entities.Insurance;
import com.Hospital_Management_System.entities.MedicalRecord;
import com.Hospital_Management_System.entities.Patient;
import com.Hospital_Management_System.exceptions.PatientException;
import com.Hospital_Management_System.payload.*;
import com.Hospital_Management_System.repositories.AppointmentRepository;
import com.Hospital_Management_System.repositories.InsuranceRepository;
import com.Hospital_Management_System.repositories.MedicalRecordRepository;
import com.Hospital_Management_System.utilities.PatientResponse;
import com.Hospital_Management_System.repositories.PatientRepository;
import com.Hospital_Management_System.services.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient = mapToEntity(patientDto);
        Patient save = patientRepository.save(patient);
        return mapToDto(save);
    }

    @Override
    public PatientDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientException("Patient not found with ID:" + id)
        );
        PatientDto patientDto = mapToDto(patient);

        List<InsuranceDto> insurances = patientDto.getInsurances();
        List<MedicalRecordDto> medicalRecords = patientDto.getMedicalRecords();
        List<AppointmentDto> appointments = patientDto.getAppointments();
        List<BillingDto> billings = patientDto.getBillings();


        return patientDto;
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.findById(id).orElseThrow(
                () -> new PatientException("Patient not found with ID:" + id)
        );
        patientRepository.deleteById(id);
    }




    @Override
    public PatientResponse patientList(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Patient> patientsPage = patientRepository.findAll(pageable);

        List<Patient> content = patientsPage.getContent();

        List<PatientDto> patients = content.stream().map(this::mapToDto).collect(Collectors.toList());

        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setPatients(patients);
        patientResponse.setPageNo(patientsPage.getNumber());
        patientResponse.setPageSize(patientsPage.getSize());
        patientResponse.setTotalPages(patientsPage.getTotalPages());
        patientResponse.setTotalElements(patientsPage.getTotalElements());
        patientResponse.setLast(patientsPage.isLast());
        return patientResponse;


    }

    @Override
    public PatientDto updatePatient(Long id, PatientDto patientDto) {
        // Retrieve the existing patient by ID
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientException("Patient not found with ID:" + id));

        // Update the patient fields with values from patientDto
        patient.setName(patientDto.getName());
        patient.setAddress(patientDto.getAddress());
        patient.setGender(patientDto.getGender());
        patient.setEmail(patientDto.getEmail());
        patient.setDateOfBirth(patientDto.getDateOfBirth());
        patient.setEmergencyContactPhone(patientDto.getEmergencyContactPhone());
        patient.setInsurancePolicyNumber(patientDto.getInsurancePolicyNumber());
        patient.setMedicalHistory(patientDto.getMedicalHistory());
        patient.setPhoneNumber(patientDto.getPhoneNumber());

        // Save the updated patient back to the repository
        Patient updatedPatient = patientRepository.save(patient);

        return mapToDto(updatedPatient);
    }



    //----------//Conversion from DTO to ENTITY and VICE-VERSA-----//--------//

    public Patient mapToEntity(PatientDto patientDto){
       return modelMapper.map(patientDto,Patient.class);
    }

    public PatientDto mapToDto(Patient patient){
        return modelMapper.map(patient,PatientDto.class);
    }
}
