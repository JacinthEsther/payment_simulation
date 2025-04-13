package com.paymentProcessing.dtos;



import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Setter
@Getter
public class PaymentRequest {

    private UUID studentId;
    private UUID parentId;
    private BigDecimal paymentAmount;
    private BigDecimal feeOrDiscountRate;
}
