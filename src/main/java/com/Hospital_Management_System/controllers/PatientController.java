package com.Hospital_Management_System.controllers;

import com.Hospital_Management_System.exceptions.PatientException;
import com.Hospital_Management_System.payload.PatientDto;
import com.Hospital_Management_System.utilities.PatientResponse;
import com.Hospital_Management_System.services.PatientService;
import com.Hospital_Management_System.utilities.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto) {
        logger.info("Received a request to create a new patient.");
        try {
            PatientDto patient = patientService.createPatient(patientDto);
            logger.info("Patient created successfully.");
            return new ResponseEntity<>(patient, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("An error occurred while creating a patient: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable("id") Long id) {
        logger.info("Received a request to retrieve patient by ID: {}", id);
        try {
            PatientDto patientById = patientService.getPatientById(id);
            logger.info("Patient found and retrieved successfully.");
            return new ResponseEntity<>(patientById, HttpStatus.OK);
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.warn("Invalid request parameter: {}", illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Patient not found or an error occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable("id") Long id) {
        logger.info("Received a request to delete a patient by ID: {}", id);
        try {
            patientService.deletePatient(id);
            logger.info("Patient deleted successfully.");
            return new ResponseEntity<>("Patient deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the patient: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while deleting the patient", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<PatientResponse> patientList(
            @RequestParam(value= "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE,required=false) int pageSize,
            @RequestParam(value= "sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY, required= false) String sortBy,
            @RequestParam(value= "sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required= false) String sortDir
    ) {
        logger.info("Received a request to retrieve a list of patients with pagination. PageNo: {}, PageSize: {}, SortBy: {}, SortDir: {}",
                pageNo, pageSize, sortBy, sortDir);
        try {
            PatientResponse patientResponse = patientService.patientList(pageNo, pageSize, sortBy, sortDir);
            logger.info("Retrieved a list of patients successfully.");
            return new ResponseEntity<>(patientResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the list of patients: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@Valid @PathVariable("id") Long id, @RequestBody PatientDto patientDto) {
        logger.info("Received a request to update patient with ID: {}", id);
        try {
            PatientDto updatedPatient = patientService.updatePatient(id, patientDto);
            logger.info("Patient updated successfully.");
            return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
        } catch (PatientException e) {
            logger.error("An error occurred while updating the patient: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

