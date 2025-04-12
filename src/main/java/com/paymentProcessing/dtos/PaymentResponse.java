package com.paymentProcessing.dtos;
import java.math.BigDecimal;
import java.util.UUID;

public class PaymentResponse {

    private UUID paymentId;
    private UUID parentId;
    private UUID studentId;
    private BigDecimal amount;
}
