package com.paymentProcessing.service.exceptions;

import java.util.UUID;

public class ParentNotFoundException extends RuntimeException {
    public ParentNotFoundException(UUID parentId) {
        super(String.valueOf(parentId));
    }
}
