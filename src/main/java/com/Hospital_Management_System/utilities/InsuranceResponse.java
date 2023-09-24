package com.Hospital_Management_System.utilities;

import com.Hospital_Management_System.payload.DoctorDto;
import com.Hospital_Management_System.payload.InsuranceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceResponse {

    private List<InsuranceDto> insurance;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
