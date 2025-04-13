package com.paymentProcessing.dtos;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PaymentResponse {

    private UUID paymentId;
    private String parentName;
    private String studentName;
    private BigDecimal adjustedAmount;
    private BigDecimal updatedParentBalance;
    private BigDecimal updatedStudentBalance;
}
