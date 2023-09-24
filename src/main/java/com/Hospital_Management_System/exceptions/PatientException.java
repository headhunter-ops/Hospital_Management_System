package com.Hospital_Management_System.exceptions;

public class PatientException extends RuntimeException{

    public PatientException(String message){
        super(message);
    }

    public PatientException(String message, Throwable cause) {
        super(message, cause);
    }
}
