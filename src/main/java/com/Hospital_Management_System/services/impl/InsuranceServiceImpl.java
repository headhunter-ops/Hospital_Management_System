package com.Hospital_Management_System.services.impl;

import com.Hospital_Management_System.entities.Insurance;
import com.Hospital_Management_System.entities.MedicalRecord;
import com.Hospital_Management_System.entities.Patient;
import com.Hospital_Management_System.exceptions.InsuranceException;
import com.Hospital_Management_System.exceptions.PatientException;
import com.Hospital_Management_System.payload.InsuranceDto;
import com.Hospital_Management_System.payload.MedicalRecordDto;
import com.Hospital_Management_System.repositories.InsuranceRepository;
import com.Hospital_Management_System.repositories.PatientRepository;
import com.Hospital_Management_System.services.InsuranceService;
import com.Hospital_Management_System.utilities.InsuranceResponse;
import com.Hospital_Management_System.utilities.MedicalRecordResponse;
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
public class InsuranceServiceImpl implements InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public InsuranceDto createInsurance(InsuranceDto insuranceDto) {
        Long patientId = insuranceDto.getPatientId();
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new PatientException("Patient not found with ID:" + patientId)
        );
        Insurance insurance= new Insurance();
        insurance.setPatient(patient);
        insurance.setCoverageAmount(insuranceDto.getCoverageAmount());
        insurance.setPolicyNumber(insuranceDto.getPolicyNumber());
        Insurance save = insuranceRepository.save(insurance);
        return mapToDto(save);
    }

    @Override
    public InsuranceDto getInsuranceById(Long id) {
        Insurance insurance = insuranceRepository.findById(id).orElseThrow(
                () -> new InsuranceException("Insurance not found with ID:" + id)
        );
        return mapToDto(insurance);
    }

    @Override
    public InsuranceDto updateInsurance(InsuranceDto insuranceDto, Long id) {
        Insurance insurance = insuranceRepository.findById(id).orElseThrow(
                () -> new InsuranceException("Insurance not found with ID:" + id)
        );

        insurance.setPolicyNumber(insurance.getPolicyNumber());
        insurance.setCoverageAmount(insurance.getCoverageAmount());
        Insurance save = insuranceRepository.save(insurance);
        return mapToDto(save);


    }

    @Override
    public void deleteInsurance(Long id) {

    }

    @Override
    public InsuranceResponse insuranceList(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Insurance> insurancePage = insuranceRepository.findAll(pageable);

        List<Insurance> content = insurancePage.getContent();

        List<InsuranceDto> insuranceList = content.stream().map(this::mapToDto).collect(Collectors.toList());

        InsuranceResponse insuranceResponse= new InsuranceResponse();
        insuranceResponse.setInsurance(insuranceList);
        insuranceResponse.setPageNo(insurancePage.getNumber());
        insuranceResponse.setPageSize(insurancePage.getSize());
        insuranceResponse.setTotalPages(insurancePage.getTotalPages());
        insuranceResponse.setTotalElements(insurancePage.getTotalElements());
        insuranceResponse.setLast(insurancePage.isLast());
        return insuranceResponse;

        // The code is organized to handle pagination and sorting efficiently using Spring Data JPA features
    }


    //------------//--Conversion from entity to Dto and vice versa--/-------------//
    public Insurance mapToEntity(InsuranceDto insuranceDto){
        return modelMapper.map(insuranceDto,Insurance.class);
    }

    public InsuranceDto mapToDto(Insurance insurance){
        return modelMapper.map(insurance,InsuranceDto.class);

    }
}
