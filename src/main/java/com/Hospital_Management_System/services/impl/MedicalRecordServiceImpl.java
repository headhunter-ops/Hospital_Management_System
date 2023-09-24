package com.Hospital_Management_System.services.impl;

import com.Hospital_Management_System.entities.MedicalRecord;
import com.Hospital_Management_System.entities.Patient;
import com.Hospital_Management_System.exceptions.MedicalRecordException;
import com.Hospital_Management_System.exceptions.PatientException;
import com.Hospital_Management_System.payload.MedicalRecordDto;
import com.Hospital_Management_System.repositories.MedicalRecordRepository;
import com.Hospital_Management_System.repositories.PatientRepository;
import com.Hospital_Management_System.services.MedicalRecordService;
import com.Hospital_Management_System.utilities.DoctorResponse;
import com.Hospital_Management_System.utilities.MedicalRecordResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public MedicalRecordDto createMedicalRecordWithPatientId(MedicalRecordDto medicalRecordDto) {
        Long patientId = medicalRecordDto.getPatientId();
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new PatientException("Patient not found with ID:" + patientId)
        );
        // Create a new MedicalRecord entity and set its properties
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setDiagnosis(medicalRecordDto.getDiagnosis());
        medicalRecord.setPrescriptions(medicalRecordDto.getPrescriptions());
        medicalRecord.setTestResults(medicalRecordDto.getTestResults());
        medicalRecord.setPatient(patient);

        // Save the MedicalRecord entity to the database
        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);

        // Map the saved entity back to a DTO and return it
        return mapToDto(savedMedicalRecord);


    }

    @Override
    public MedicalRecordDto getRecordById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(
                () -> new MedicalRecordException("Medical record not found with ID:" + id)
        );
        return mapToDto(medicalRecord);
    }

    @Override
    public MedicalRecordDto updateRecordById(MedicalRecordDto medicalRecordDto, Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(
                () -> new MedicalRecordException("Medical record not found with ID:" + id)
        );
        medicalRecord.setDiagnosis(medicalRecordDto.getDiagnosis());
        medicalRecord.setPrescriptions(medicalRecordDto.getPrescriptions());
        medicalRecord.setTestResults(medicalRecordDto.getTestResults());
        MedicalRecord save = medicalRecordRepository.save(medicalRecord);
        return mapToDto(save);
    }

    @Override
    public void deleteRecordById(Long id) {
         medicalRecordRepository.findById(id).orElseThrow(
                () -> new MedicalRecordException("Medical record not found with ID:" + id)
        );
        medicalRecordRepository.deleteById(id);
    }

    @Override
    public MedicalRecordResponse medicalRecordList(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<MedicalRecord> medicalRecordPage = medicalRecordRepository.findAll(pageable);

        List<MedicalRecord> content = medicalRecordPage.getContent();

        List<MedicalRecordDto> medicalRecords = content.stream().map(this::mapToDto).collect(Collectors.toList());

        MedicalRecordResponse medicalRecordResponse = new MedicalRecordResponse();
        medicalRecordResponse.setMedicalRecords(medicalRecords);
        medicalRecordResponse.setPageNo(medicalRecordPage.getNumber());
        medicalRecordResponse.setPageSize(medicalRecordPage.getSize());
        medicalRecordResponse.setTotalPages(medicalRecordPage.getTotalPages());
        medicalRecordResponse.setTotalElements(medicalRecordPage.getTotalElements());
        medicalRecordResponse.setLast(medicalRecordPage.isLast());
        return medicalRecordResponse;

    }

    //-------------//--Conversion from entity to dto and vice vera--//--------//
    public MedicalRecord mapToEntity(MedicalRecordDto medicalRecordDto){
        return modelMapper.map(medicalRecordDto,MedicalRecord.class);
    }

    public MedicalRecordDto mapToDto(MedicalRecord medicalRecord){
        return modelMapper.map(medicalRecord,MedicalRecordDto.class);
    }
}
