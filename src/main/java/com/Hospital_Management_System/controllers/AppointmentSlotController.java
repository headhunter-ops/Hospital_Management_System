package com.Hospital_Management_System.controllers;

import com.Hospital_Management_System.exceptions.PatientException;
import com.Hospital_Management_System.payload.AppointmentSlotDto;
import com.Hospital_Management_System.payload.DoctorDto;
import com.Hospital_Management_System.services.AppointmentSlotService;
import com.Hospital_Management_System.utilities.AppConstants;
import com.Hospital_Management_System.utilities.AppointmentSlotResponse;
import com.Hospital_Management_System.utilities.DoctorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/slot")
public class AppointmentSlotController {

    @Autowired
    private AppointmentSlotService appointmentSlotService;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentSlotController.class);
    @PostMapping
    public ResponseEntity<AppointmentSlotDto> createSlot(@Valid @RequestBody AppointmentSlotDto appointmentSlotDto){
        logger.info("Received a request to create a new Slot.");
        try{
            AppointmentSlotDto appointmentSlot = appointmentSlotService.createAppointmentSlot(appointmentSlotDto);
            logger.info("Slot registered successfully");
            return new ResponseEntity<>(appointmentSlot, HttpStatus.CREATED);
        }catch (Exception e) {
            logger.error("An error occurred while creating a Slot: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSlotById(@PathVariable("id") Long id) {
        logger.info("Received a request to retrieve Slot by ID: {}", id);
        try {
            AppointmentSlotDto appointmentSlotById = appointmentSlotService.getAppointmentSlotById(id);
            logger.info("Slot found and retrieved successfully.");
            return new ResponseEntity<>(appointmentSlotById, HttpStatus.OK);
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.warn("Invalid request parameter: {}", illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Slot not found or an error occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>("Slot not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSlotById(@PathVariable("id") Long id) {
        logger.info("Received a request to Slot a doctor by ID: {}", id);
        try {
            appointmentSlotService.deleteAppointmentSlot(id);
            logger.info("Slot deleted successfully.");
            return new ResponseEntity<>("Slot deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the Slot: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred while deleting the Slot", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<AppointmentSlotResponse> doctorList(
            @RequestParam(value= "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE,required=false) int pageSize,
            @RequestParam(value= "sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY, required= false) String sortBy,
            @RequestParam(value= "sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required= false) String sortDir
    ) {
        logger.info("Received a request to retrieve a list of Slots with pagination. PageNo: {}, PageSize: {}, SortBy: {}, SortDir: {}",
                pageNo, pageSize, sortBy, sortDir);
        try {
            AppointmentSlotResponse appointmentSlotResponse = appointmentSlotService.appointmentSlotList(pageNo, pageSize, sortBy, sortDir);
            logger.info("Retrieved a list of Slots successfully.");
            return new ResponseEntity<>(appointmentSlotResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the list of Slots: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSlot(@Valid @PathVariable("id") Long id, @RequestBody AppointmentSlotDto appointmentSlotDto) {
        logger.info("Received a request to update slot with ID: {}", id);
        try {
            AppointmentSlotDto appointmentSlotDto1 = appointmentSlotService.updateAppointmentSlotDto(id, appointmentSlotDto);
            logger.info("Slot updated successfully.");
            return new ResponseEntity<>(appointmentSlotDto1, HttpStatus.OK);
        } catch (PatientException e) {
            logger.error("An error occurred while updating the Slot: {}", e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
