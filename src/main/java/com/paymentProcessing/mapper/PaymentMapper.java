package com.paymentProcessing.mapper;


import com.paymentProcessing.dtos.PaymentResponse;
import com.paymentProcessing.entities.Parent;
import com.paymentProcessing.entities.Payment;
import com.paymentProcessing.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "student", source = "student")
    @Mapping(target = "parent", source = "parent")
    @Mapping(target = "amount", source = "paymentAmount")
    @Mapping(target = "id", ignore = true)
    Payment paymentRequestToPayment(BigDecimal paymentAmount, Student student, Parent parent);

    PaymentResponse paymentToPaymentResponse(Payment payment);
}
