package com.Hospital_Management_System.services.impl;

import com.Hospital_Management_System.entities.Appointment;
import com.Hospital_Management_System.entities.Doctor;
import com.Hospital_Management_System.entities.MedicalRecord;
import com.Hospital_Management_System.entities.Patient;
import com.Hospital_Management_System.exceptions.AppointmentException;
import com.Hospital_Management_System.exceptions.DoctorException;
import com.Hospital_Management_System.exceptions.PatientException;
import com.Hospital_Management_System.payload.AppointmentDto;
import com.Hospital_Management_System.repositories.AppointmentRepository;
import com.Hospital_Management_System.repositories.DoctorRepository;
import com.Hospital_Management_System.repositories.PatientRepository;
import com.Hospital_Management_System.services.AppointmentService;
import com.Hospital_Management_System.utilities.AppointmentResponse;
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
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        Long patientId = appointmentDto.getPatientId();
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new PatientException("Patient not found with ID:" + patientId)
        );

        Long doctorId = appointmentDto.getDoctorId();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new DoctorException("Doctor not found with ID:" + doctorId)
        );
        Appointment appointment= new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(appointmentDto.getAppointmentDateTime());

        Appointment save = appointmentRepository.save(appointment);
        return mapToDto(save);

    }

    @Override
    public AppointmentDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new AppointmentException("Appointment not found with ID:" + id)
        );
        return mapToDto(appointment);
    }

    @Override
    public AppointmentDto updateAppointment(Long id, AppointmentDto appointmentDto) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new AppointmentException("Appointment not found with ID:" + id)
        );
        appointment.setAppointmentDateTime(appointmentDto.getAppointmentDateTime());
        Appointment save = appointmentRepository.save(appointment);
        return mapToDto(save);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.findById(id).orElseThrow(
                () -> new AppointmentException("Appointment not found with ID:" + id)
        );
        appointmentRepository.deleteById(id);
    }

    @Override
    public AppointmentResponse appointmentList(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Appointment> appointmentPage = appointmentRepository.findAll(pageable);
        List<Appointment> content = appointmentPage.getContent();
        List<AppointmentDto> appointmentList = content.stream().map(this::mapToDto).collect(Collectors.toList());

        AppointmentResponse appointmentResponse= new AppointmentResponse();
        appointmentResponse.setAppointments(appointmentList);
        appointmentResponse.setPageNo(appointmentPage.getNumber());
        appointmentResponse.setPageSize(appointmentPage.getSize());
        appointmentResponse.setTotalPages(appointmentPage.getTotalPages());
        appointmentResponse.setTotalElements(appointmentPage.getTotalElements());
        appointmentResponse.setLast(appointmentPage.isLast());
        return appointmentResponse;

    }

    //--------------//--Conversion from Entity to DTO and vice versa--//------------//
    public Appointment mapToEntity(AppointmentDto appointmentDto){
        return modelMapper.map(appointmentDto,Appointment.class);
    }

    public AppointmentDto mapToDto(Appointment appointment){
        return modelMapper.map(appointment,AppointmentDto.class);
    }
}
