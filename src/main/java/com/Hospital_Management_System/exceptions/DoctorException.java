package com.Hospital_Management_System.exceptions;

public class DoctorException extends RuntimeException{

    public DoctorException(String message){
        super(message);
    }

    public DoctorException(String message, Throwable cause) {
        super(message, cause);
    }
}

