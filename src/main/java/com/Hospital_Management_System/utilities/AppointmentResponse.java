package com.Hospital_Management_System.utilities;

import com.Hospital_Management_System.payload.AppointmentDto;
import com.Hospital_Management_System.payload.DoctorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    private List<AppointmentDto> appointments;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
