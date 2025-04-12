package com.paymentProcessing.mapper;


import com.paymentProcessing.dtos.PaymentRequest;
import com.paymentProcessing.dtos.PaymentResponse;
import com.paymentProcessing.entities.Parent;
import com.paymentProcessing.entities.Payment;
import com.paymentProcessing.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "paymentRequest.studentId", target = "student.id")
    @Mapping(source = "paymentRequest.studentId", target = "parent.id")
    Payment paymentRequestToPayment(PaymentRequest paymentRequest, Student student, Parent parent);

    PaymentResponse paymentToPaymentResponse(Payment payment);
}
