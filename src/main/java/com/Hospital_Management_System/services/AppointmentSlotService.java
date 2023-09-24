package com.Hospital_Management_System.services;

import com.Hospital_Management_System.payload.AppointmentSlotDto;
import com.Hospital_Management_System.utilities.AppointmentSlotResponse;

public interface AppointmentSlotService {

    public AppointmentSlotDto createAppointmentSlot(AppointmentSlotDto appointmentSlotDto);
    public AppointmentSlotDto getAppointmentSlotById(Long id);
    public AppointmentSlotDto updateAppointmentSlotDto(Long id, AppointmentSlotDto appointmentSlotDto);
    public void deleteAppointmentSlot(Long id);
    public AppointmentSlotResponse appointmentSlotList(int pageNo, int pageSize, String sortBy, String sortDir);
}
