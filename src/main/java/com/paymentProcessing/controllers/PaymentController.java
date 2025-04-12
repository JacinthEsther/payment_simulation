package com.paymentProcessing.controllers;


import com.paymentProcessing.dtos.PaymentRequest;
import com.paymentProcessing.dtos.PaymentResponse;
import com.paymentProcessing.service.serviceInterface.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
        logger.info("Processing payment for parent {} and student {}", request.getParentId(), request.getStudentId());
        PaymentResponse response = paymentService.processPayment(request);
        logger.info("Payment processed: {}", response);
        return ResponseEntity.ok(response);
    }
}

