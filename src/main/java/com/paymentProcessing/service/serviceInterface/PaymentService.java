package com.paymentProcessing.service.serviceInterface;


import com.paymentProcessing.dtos.PaymentRequest;
import com.paymentProcessing.dtos.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}
