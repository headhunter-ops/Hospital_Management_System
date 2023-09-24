package com.Hospital_Management_System.exceptions;

public class AppointmentException extends RuntimeException{
    public AppointmentException(String message){
        super(message);
    }

    public AppointmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
