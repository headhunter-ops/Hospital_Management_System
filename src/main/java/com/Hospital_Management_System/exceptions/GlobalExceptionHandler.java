package com.Hospital_Management_System.exceptions;

import com.Hospital_Management_System.entities.Doctor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        // Handle validation errors (e.g., when @Valid fails)
        String errorMessage = "Validation failed: " + ex.getFieldError().getDefaultMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientException.class)
    public ResponseEntity<String> handlePatientException(PatientException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DoctorException.class)
    public ResponseEntity<String> handleDoctorException(DoctorException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MedicalRecordException.class)
    public ResponseEntity<String> handleMedicalRecordException(MedicalRecordException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsuranceException.class)
    public ResponseEntity<String> handleInsuranceException(InsuranceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AppointmentException.class)
    public ResponseEntity<String> handleAppointmentException(AppointmentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppointmentSlotException.class)
    public ResponseEntity<String> handleAppointmentSlotException(AppointmentSlotException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}