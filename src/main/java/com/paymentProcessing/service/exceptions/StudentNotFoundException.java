package com.paymentProcessing.service.exceptions;

import java.util.UUID;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(UUID studentId) {
        super(String.valueOf(studentId));
    }
}
