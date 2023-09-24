package com.Hospital_Management_System.exceptions;

public class BillingException extends RuntimeException {
    public BillingException(String message){
        super(message);
    }

    public BillingException(String message, Throwable cause) {
        super(message, cause);
    }
}
