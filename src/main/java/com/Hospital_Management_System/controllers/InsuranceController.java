package com.Hospital_Management_System.controllers;

import com.Hospital_Management_System.exceptions.PatientException;
import com.Hospital_Management_System.payload.DoctorDto;
import com.Hospital_Management_System.payload.InsuranceDto;
import com.Hospital_Management_System.services.InsuranceService;
import com.Hospital_Management_System.utilities.AppConstants;
import com.Hospital_Management_System.utilities.DoctorResponse;
import com.Hospital_Management_System.utilities.InsuranceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

    @Autowired
    private InsuranceService insuranceService;

    private static final Logger logger = LoggerFactory.getLogger(InsuranceController.class);

    @PostMapping
    public ResponseEntity<InsuranceDto> createInsurance(@Valid @RequestBody InsuranceDto insuranceDto){
        logger.info("Received a request to create a new Insurance.");
        try{
            InsuranceDto insurance = insuranceService.createInsurance(insuranceDto);
            logger.info("Insurance registered successfully");
            return new ResponseEntity<>(insurance, HttpStatus.CREATED);
        }catch (Exception e) {
            logger.error("An error occurred while creating a insurance: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInsuranceById(@PathVariable("id") Long id) {
        logger.info("Received a request to retrieve insurance by ID: {}", id);
        try {
            InsuranceDto insuranceById = insuranceService.getInsuranceById(id);
            logger.info("Insurance found and retrieved successfully.");
            return new ResponseEntity<>(insuranceById, HttpStatus.OK);
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.warn("Invalid request parameter: {}", illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Insurance not found or an error occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>("Insurance not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInsuranceById(@PathVariable("id") Long id) {
        logger.info("Received a request to delete a insurance by ID: {}", id);
        try {
            insuranceService.deleteInsurance(id);
            logger.info("Insurance deleted successfully.");
            return new ResponseEntity<>("Insurance deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the insurance: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while deleting the insurance", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<InsuranceResponse> insuranceList(
            @RequestParam(value= "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE,required=false) int pageSize,
            @RequestParam(value= "sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY, required= false) String sortBy,
            @RequestParam(value= "sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required= false) String sortDir
    ) {
        logger.info("Received a request to retrieve a list of insurance with pagination. PageNo: {}, PageSize: {}, SortBy: {}, SortDir: {}",
                pageNo, pageSize, sortBy, sortDir);
        try {
            InsuranceResponse insuranceResponse = insuranceService.insuranceList(pageNo, pageSize, sortBy, sortDir);
            logger.info("Retrieved a list of insurances successfully.");
            return new ResponseEntity<>(insuranceResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the list of insurances: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInsurance(@Valid @PathVariable("id") Long id, @RequestBody InsuranceDto insuranceDto) {
        logger.info("Received a request to update doctor with ID: {}", id);
        try {
            InsuranceDto insuranceDto1 = insuranceService.updateInsurance(insuranceDto, id);
            logger.info("Insurance updated successfully.");
            return new ResponseEntity<>(insuranceDto1, HttpStatus.OK);
        } catch (PatientException e) {
            logger.error("An error occurred while updating the insurance: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
