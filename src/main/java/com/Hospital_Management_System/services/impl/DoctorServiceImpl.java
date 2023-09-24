package com.Hospital_Management_System.services.impl;


import com.Hospital_Management_System.entities.Doctor;
import com.Hospital_Management_System.entities.Patient;
import com.Hospital_Management_System.exceptions.DoctorException;
import com.Hospital_Management_System.payload.AppointmentDto;
import com.Hospital_Management_System.payload.DoctorDto;
import com.Hospital_Management_System.payload.PatientDto;
import com.Hospital_Management_System.repositories.DoctorRepository;
import com.Hospital_Management_System.services.DoctorService;
import com.Hospital_Management_System.utilities.DoctorResponse;
import com.Hospital_Management_System.utilities.PatientResponse;
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
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DoctorDto createDoctor(DoctorDto doctorDto) {
        Doctor doctor = mapToEntity(doctorDto);
        Doctor save = doctorRepository.save(doctor);
        return mapToDto(save);
    }

    @Override
    public DoctorDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new DoctorException("Doctor not found with ID:" + id)
        );
        DoctorDto doctorDto = mapToDto(doctor);
        List<AppointmentDto> appointments = doctorDto.getAppointments();
        return doctorDto;
    }

    @Override
    public void deleteDoctorById(Long id) {
         doctorRepository.findById(id).orElseThrow(
                () -> new DoctorException("Doctor not found with ID:" + id)
        );
        doctorRepository.deleteById(id);
    }

    @Override
    public DoctorDto updateDoctor(Long id, DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new DoctorException("Doctor not found with ID:" + id)
        );
        doctor.setName(doctorDto.getName());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setPhoneNumber(doctorDto.getPhoneNumber());
        doctor.setConsultationFee(doctorDto.getConsultationFee());
        Doctor save = doctorRepository.save(doctor);
        return mapToDto(save);

    }

    @Override
    public DoctorResponse doctorList(int pageNo, int pageSize, String sortBy, String sortDir) {

        /*This code configures the sorting order for the list of doctors based on
        the sortDir and sortBy parameters passed to the method.
        It checks if sortDir is equal to "ASC" (ascending) and then creates a Sort object accordingly,
        either in ascending or descending order, based on the sortBy parameter*/
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        /*Here, a Pageable object is created using the PageRequest.of method,
         which takes the current page number (pageNo), page size (pageSize),
         and the sorting configuration (sort).
         This Pageable object is used for paginating the results.*/
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        /*The code fetches a page of doctors from a repository (doctorRepository)
         using the findAll method provided by Spring Data JPA.
         This method takes the Pageable object for pagination and sorting.*/
        Page<Doctor> doctorPage = doctorRepository.findAll(pageable);


        //The getContent method of the Page object retrieves the list of doctors in the current page.
        List<Doctor> content = doctorPage.getContent();

        List<DoctorDto> doctors = content.stream().map(this::mapToDto).collect(Collectors.toList());

        DoctorResponse doctorResponse = new DoctorResponse();
        doctorResponse.setDoctors(doctors);
        doctorResponse.setPageNo(doctorPage.getNumber());
        doctorResponse.setPageSize(doctorPage.getSize());
        doctorResponse.setTotalPages(doctorPage.getTotalPages());
        doctorResponse.setTotalElements(doctorPage.getTotalElements());
        doctorResponse.setLast(doctorPage.isLast());
        return doctorResponse;

       // The code is organized to handle pagination and sorting efficiently using Spring Data JPA features
    }



    //------------//--Conversion from ENTITY to DTO and VICE_VERSA--//--------//
    public Doctor mapToEntity(DoctorDto doctorDto){
        return modelMapper.map(doctorDto,Doctor.class);
    }

    public DoctorDto mapToDto(Doctor doctor){
        return modelMapper.map(doctor,DoctorDto.class);
    }
}
