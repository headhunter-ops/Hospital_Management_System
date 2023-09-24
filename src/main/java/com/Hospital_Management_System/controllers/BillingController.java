package com.Hospital_Management_System.controllers;

import com.Hospital_Management_System.exceptions.PatientException;
import com.Hospital_Management_System.payload.AppointmentDto;
import com.Hospital_Management_System.payload.BillingDto;
import com.Hospital_Management_System.services.BillingService;
import com.Hospital_Management_System.utilities.AppConstants;
import com.Hospital_Management_System.utilities.AppointmentResponse;
import com.Hospital_Management_System.utilities.BillingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    private final Logger logger= LoggerFactory.getLogger(BillingController.class);

    @PostMapping
    public ResponseEntity<BillingDto> createBill(@Valid @RequestBody BillingDto billingDto){
        logger.info("Received a request to create a new bill.");
        try{
            BillingDto billing = billingService.createBilling(billingDto);
            logger.info("Bill generated successfully");
            return new ResponseEntity<>(billing, HttpStatus.CREATED);
        }catch (Exception e) {
            logger.error("An error occurred while creating a bill: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBillById(@PathVariable("id") Long id) {
        logger.info("Received a request to retrieve bill by ID: {}", id);
        try {
            BillingDto billingById = billingService.getBillingById(id);
            logger.info("Bill found and retrieved successfully.");
            return new ResponseEntity<>(billingById, HttpStatus.OK);
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.warn("Invalid request parameter: {}", illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Bill not found or an error occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>("Bill not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBillById(@PathVariable("id") Long id) {
        logger.info("Received a request to delete a bill by ID: {}", id);
        try {
            billingService.deleteBill(id);

            logger.info("Bill deleted successfully.");
            return new ResponseEntity<>("Bill deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the Bill: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while deleting the Bill", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<BillingResponse> billList(
            @RequestParam(value= "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE,required=false) int pageSize,
            @RequestParam(value= "sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY, required= false) String sortBy,
            @RequestParam(value= "sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required= false) String sortDir
    ) {
        logger.info("Received a request to retrieve a list of Bills with pagination. PageNo: {}, PageSize: {}, SortBy: {}, SortDir: {}",
                pageNo, pageSize, sortBy, sortDir);
        try {
            BillingResponse billingResponse = billingService.billingList(pageNo, pageSize, sortBy, sortDir);
            logger.info("Retrieved a list of bills successfully.");
            return new ResponseEntity<>(billingResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the list of bills: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBill(@Valid @PathVariable("id") Long id, @RequestBody BillingDto billingDto) {
        logger.info("Received a request to update Appointment with ID: {}", id);
        try {
            BillingDto billingDto1 = billingService.updateBilling(id,billingDto);
            logger.info("Bill updated successfully.");
            return new ResponseEntity<>(billingDto1, HttpStatus.OK);
        } catch (PatientException e) {
            logger.error("An error occurred while updating the bill: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
