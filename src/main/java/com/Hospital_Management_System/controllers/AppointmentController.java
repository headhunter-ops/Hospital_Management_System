package com.Hospital_Management_System.controllers;

import com.Hospital_Management_System.exceptions.PatientException;
import com.Hospital_Management_System.payload.AppointmentDto;
import com.Hospital_Management_System.payload.DoctorDto;
import com.Hospital_Management_System.services.AppointmentService;
import com.Hospital_Management_System.utilities.AppConstants;
import com.Hospital_Management_System.utilities.AppointmentResponse;
import com.Hospital_Management_System.utilities.DoctorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentDto appointmentDto){
        logger.info("Received a request to create a new appointment.");
        try{
            AppointmentDto appointment = appointmentService.createAppointment(appointmentDto);
            logger.info("Appointment registered successfully");
            return new ResponseEntity<>(appointment, HttpStatus.CREATED);
        }catch (Exception e) {
            logger.error("An error occurred while creating a appointment: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable("id") Long id) {
        logger.info("Received a request to retrieve appointment by ID: {}", id);
        try {
            AppointmentDto appointmentById = appointmentService.getAppointmentById(id);
            logger.info("Appointment found and retrieved successfully.");
            return new ResponseEntity<>(appointmentById, HttpStatus.OK);
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.warn("Invalid request parameter: {}", illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Appointment not found or an error occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>("Appointment not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable("id") Long id) {
        logger.info("Received a request to delete a appointment by ID: {}", id);
        try {
          appointmentService.deleteAppointment(id);

            logger.info("Appointment deleted successfully.");
            return new ResponseEntity<>("Appointment deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the Appointment: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while deleting the Appointment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<AppointmentResponse> appointmentList(
            @RequestParam(value= "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE,required=false) int pageSize,
            @RequestParam(value= "sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY, required= false) String sortBy,
            @RequestParam(value= "sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required= false) String sortDir
    ) {
        logger.info("Received a request to retrieve a list of doctors with pagination. PageNo: {}, PageSize: {}, SortBy: {}, SortDir: {}",
                pageNo, pageSize, sortBy, sortDir);
        try {
            AppointmentResponse appointmentResponse = appointmentService.appointmentList(pageNo, pageSize, sortBy, sortDir);
            logger.info("Retrieved a list of appointments successfully.");
            return new ResponseEntity<>(appointmentResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the list of appointments: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@Valid @PathVariable("id") Long id, @RequestBody AppointmentDto appointmentDto) {
        logger.info("Received a request to update Appointment with ID: {}", id);
        try {
            AppointmentDto appointmentDto1 = appointmentService.updateAppointment(id, appointmentDto);
            logger.info("Appointment updated successfully.");
            return new ResponseEntity<>(appointmentDto1, HttpStatus.OK);
        } catch (PatientException e) {
            logger.error("An error occurred while updating the Appointment: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
