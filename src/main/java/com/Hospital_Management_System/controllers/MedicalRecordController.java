package com.Hospital_Management_System.controllers;

import com.Hospital_Management_System.exceptions.PatientException;
import com.Hospital_Management_System.payload.DoctorDto;
import com.Hospital_Management_System.payload.MedicalRecordDto;
import com.Hospital_Management_System.services.MedicalRecordService;
import com.Hospital_Management_System.utilities.AppConstants;
import com.Hospital_Management_System.utilities.DoctorResponse;
import com.Hospital_Management_System.utilities.MedicalRecordResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/medicalRecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @PostMapping
    public ResponseEntity<MedicalRecordDto> createMedicalRecord(@Valid @RequestBody MedicalRecordDto medicalRecordDto){
        logger.info("Received a request to create a new medical record.");
        try{
            MedicalRecordDto record = medicalRecordService.createMedicalRecordWithPatientId(medicalRecordDto);
            logger.info("Medical Record registered successfully");
            return new ResponseEntity<>(record, HttpStatus.CREATED);
        }catch (Exception e) {
            logger.error("An error occurred while creating a medical record: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMedicalRecordById(@PathVariable("id") Long id) {
        logger.info("Received a request to retrieve medical record by ID: {}", id);
        try {
            MedicalRecordDto recordById = medicalRecordService.getRecordById(id);
            logger.info("Medical record found and retrieved successfully.");
            return new ResponseEntity<>(recordById, HttpStatus.OK);
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.warn("Invalid request parameter: {}", illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Medical record not found or an error occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>("Medical record not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicalRecordById(@PathVariable("id") Long id) {
        logger.info("Received a request to delete a doctor by ID: {}", id);
        try {
            medicalRecordService.deleteRecordById(id);
            logger.info("Medical record deleted successfully.");
            return new ResponseEntity<>("Medical record deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the Medical record: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while deleting the Medical record", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<MedicalRecordResponse> medicalRecordList(
            @RequestParam(value= "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE,required=false) int pageSize,
            @RequestParam(value= "sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY, required= false) String sortBy,
            @RequestParam(value= "sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required= false) String sortDir
    ) {
        logger.info("Received a request to retrieve a list of Medical record with pagination. PageNo: {}, PageSize: {}, SortBy: {}, SortDir: {}",
                pageNo, pageSize, sortBy, sortDir);
        try {

            MedicalRecordResponse medicalRecordResponse = medicalRecordService.medicalRecordList(pageNo, pageSize, sortBy, sortDir);
            logger.info("Retrieved a list of  successfully.");
            return new ResponseEntity<>(medicalRecordResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the list of Medical record: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMedicalRecord(@Valid @PathVariable("id") Long id, @RequestBody MedicalRecordDto medicalRecordDto) {
        logger.info("Received a request to update Medical record with ID: {}", id);
        try {
            MedicalRecordDto medicalRecordDto1 = medicalRecordService.updateRecordById(medicalRecordDto, id);
            logger.info("Medical Record updated successfully.");
            return new ResponseEntity<>(medicalRecordDto1, HttpStatus.OK);
        } catch (PatientException e) {
            logger.error("An error occurred while updating the Medical Record : {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
