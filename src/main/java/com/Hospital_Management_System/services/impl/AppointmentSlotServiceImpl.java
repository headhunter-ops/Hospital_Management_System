package com.Hospital_Management_System.services.impl;

import com.Hospital_Management_System.entities.AppointmentSlot;
import com.Hospital_Management_System.entities.Doctor;
import com.Hospital_Management_System.exceptions.AppointmentSlotException;
import com.Hospital_Management_System.exceptions.DoctorException;
import com.Hospital_Management_System.payload.AppointmentSlotDto;
import com.Hospital_Management_System.repositories.AppointmentSlotRepository;
import com.Hospital_Management_System.repositories.DoctorRepository;
import com.Hospital_Management_System.services.AppointmentSlotService;
import com.Hospital_Management_System.utilities.AppointmentSlotResponse;
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
public class AppointmentSlotServiceImpl implements AppointmentSlotService {

    @Autowired
    private AppointmentSlotRepository appointmentSlotRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppointmentSlotDto createAppointmentSlot(AppointmentSlotDto appointmentSlotDto) {
        Long doctorId = appointmentSlotDto.getDoctorId();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new DoctorException("Doctor not found with ID:" + doctorId)
        );
        AppointmentSlot appointmentSlot= new AppointmentSlot();
        appointmentSlot.setDoctor(doctor);
        appointmentSlot.setEndTime(appointmentSlotDto.getEndTime());
        appointmentSlot.setStartTime(appointmentSlotDto.getStartTime());
        AppointmentSlot save = appointmentSlotRepository.save(appointmentSlot);
        return mapToDto(save);
    }

    @Override
    public AppointmentSlotDto getAppointmentSlotById(Long id) {
        AppointmentSlot appointmentSlot = appointmentSlotRepository.findById(id).orElseThrow(
                () -> new AppointmentSlotException("Slot not found wth ID:" + id)
        );

        return mapToDto(appointmentSlot);
    }

    @Override
    public AppointmentSlotDto updateAppointmentSlotDto(Long id, AppointmentSlotDto appointmentSlotDto) {
        AppointmentSlot appointmentSlot = appointmentSlotRepository.findById(id).orElseThrow(
                () -> new AppointmentSlotException("Slot not found wth ID:" + id)
        );

        appointmentSlot.setStartTime(appointmentSlotDto.getStartTime());
        appointmentSlot.setEndTime(appointmentSlotDto.getEndTime());
        AppointmentSlot save = appointmentSlotRepository.save(appointmentSlot);
        return mapToDto(save);
    }

    @Override
    public void deleteAppointmentSlot(Long id) {
        AppointmentSlot appointmentSlot = appointmentSlotRepository.findById(id).orElseThrow(
                () -> new AppointmentSlotException("Slot not found wth ID:" + id)
        );

        appointmentSlotRepository.deleteById(id);
    }

    @Override
    public AppointmentSlotResponse appointmentSlotList(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<AppointmentSlot> appointmentSlotPage = appointmentSlotRepository.findAll(pageable);
        List<AppointmentSlot> content = appointmentSlotPage.getContent();
        List<AppointmentSlotDto> slotList = content.stream().map(this::mapToDto).collect(Collectors.toList());

        AppointmentSlotResponse appointmentSlotResponse= new AppointmentSlotResponse();
        appointmentSlotResponse.setSlots(slotList);
        appointmentSlotResponse.setLast(appointmentSlotPage.isLast());
        appointmentSlotResponse.setPageNo(appointmentSlotPage.getNumber());
        appointmentSlotResponse.setPageSize(appointmentSlotPage.getSize());
        appointmentSlotResponse.setTotalPages(appointmentSlotPage.getTotalPages());
        appointmentSlotResponse.setTotalElements(appointmentSlotPage.getTotalElements());
        appointmentSlotResponse.setLast(appointmentSlotPage.isLast());

        return  appointmentSlotResponse;

    }

    //------------//--Conversion from Entity to DTO and Vice versa--//----------------//
    public AppointmentSlot mapToEntity(AppointmentSlotDto appointmentSlotDto){
        return modelMapper.map(appointmentSlotDto,AppointmentSlot.class);
    }

    public AppointmentSlotDto mapToDto(AppointmentSlot appointmentSlot){
        return modelMapper.map(appointmentSlot,AppointmentSlotDto.class);
    }
}
