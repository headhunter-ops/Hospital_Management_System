package com.Hospital_Management_System.services;

import com.Hospital_Management_System.payload.AppointmentDto;
import com.Hospital_Management_System.utilities.AppointmentResponse;


public interface AppointmentService {

    public AppointmentDto createAppointment(AppointmentDto appointmentDto);
    public AppointmentDto getAppointmentById(Long id);
    public AppointmentDto updateAppointment(Long id, AppointmentDto appointmentDto);
    public void deleteAppointment(Long id);
    public AppointmentResponse appointmentList(int pageNo, int pageSize, String sortBy, String sortDir);
}
