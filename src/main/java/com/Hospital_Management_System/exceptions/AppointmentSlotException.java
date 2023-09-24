package com.Hospital_Management_System.exceptions;

public class AppointmentSlotException extends RuntimeException {
    public AppointmentSlotException(String message){
        super(message);
    }

    public AppointmentSlotException(String message, Throwable cause) {
        super(message, cause);
    }
}
