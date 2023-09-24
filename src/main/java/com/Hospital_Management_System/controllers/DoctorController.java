package com.Hospital_Management_System.controllers;

import com.Hospital_Management_System.exceptions.PatientException;
import com.Hospital_Management_System.payload.DoctorDto;
import com.Hospital_Management_System.payload.PatientDto;
import com.Hospital_Management_System.services.DoctorService;
import com.Hospital_Management_System.utilities.AppConstants;
import com.Hospital_Management_System.utilities.DoctorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto doctorDto){
        logger.info("Received a request to create a new doctor.");
        try{
            DoctorDto doctor = doctorService.createDoctor(doctorDto);
            logger.info("Doctor registered successfully");
            return new ResponseEntity<>(doctor, HttpStatus.CREATED);
        }catch (Exception e) {
            logger.error("An error occurred while creating a doctor: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable("id") Long id) {
        logger.info("Received a request to retrieve doctor by ID: {}", id);
        try {
            DoctorDto doctorById = doctorService.getDoctorById(id);
            logger.info("Doctor found and retrieved successfully.");
            return new ResponseEntity<>(doctorById, HttpStatus.OK);
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.warn("Invalid request parameter: {}", illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Doctor not found or an error occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>("Doctor not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctorById(@PathVariable("id") Long id) {
        logger.info("Received a request to delete a doctor by ID: {}", id);
        try {
            doctorService.deleteDoctorById(id);
            logger.info("Doctor deleted successfully.");
            return new ResponseEntity<>("Doctor deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the doctor: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while deleting the doctor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<DoctorResponse> doctorList(
            @RequestParam(value= "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE,required=false) int pageSize,
            @RequestParam(value= "sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY, required= false) String sortBy,
            @RequestParam(value= "sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required= false) String sortDir
    ) {
        logger.info("Received a request to retrieve a list of doctors with pagination. PageNo: {}, PageSize: {}, SortBy: {}, SortDir: {}",
                pageNo, pageSize, sortBy, sortDir);
        try {
            DoctorResponse doctorResponse = doctorService.doctorList(pageNo, pageSize, sortBy, sortDir);
            logger.info("Retrieved a list of doctors successfully.");
            return new ResponseEntity<>(doctorResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the list of doctors: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@Valid @PathVariable("id") Long id, @RequestBody DoctorDto doctorDto) {
        logger.info("Received a request to update doctor with ID: {}", id);
        try {
            DoctorDto doctorDto1 = doctorService.updateDoctor(id, doctorDto);
            logger.info("Doctor updated successfully.");
            return new ResponseEntity<>(doctorDto1, HttpStatus.OK);
        } catch (PatientException e) {
            logger.error("An error occurred while updating the doctor: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
