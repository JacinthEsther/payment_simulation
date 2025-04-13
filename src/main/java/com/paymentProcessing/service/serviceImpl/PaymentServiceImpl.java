package com.paymentProcessing.service.serviceImpl;

import com.paymentProcessing.dtos.PaymentRequest;
import com.paymentProcessing.dtos.PaymentResponse;
import com.paymentProcessing.entities.Parent;
import com.paymentProcessing.entities.Payment;
import com.paymentProcessing.entities.Student;
import com.paymentProcessing.mapper.PaymentMapper;
import com.paymentProcessing.repositories.PaymentRepository;
import com.paymentProcessing.service.exceptions.ParentNotFoundException;
import com.paymentProcessing.service.exceptions.StudentNotFoundException;
import com.paymentProcessing.service.serviceInterface.ParentService;
import com.paymentProcessing.service.serviceInterface.PaymentService;
import com.paymentProcessing.service.serviceInterface.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;
    private final ParentService parentService;
    private final StudentService studentService;
    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              ParentService parentService,
                              StudentService studentService,
                              PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.parentService = parentService;
        this.studentService = studentService;
        this.paymentMapper = paymentMapper;
    }

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        logger.info("Initiating payment: Parent={}, Student={}, Amount={}",
                paymentRequest.getParentId(), paymentRequest.getStudentId(), paymentRequest.getPaymentAmount());

        try {
            Parent parent = parentService.getParentById(paymentRequest.getParentId())
                    .orElseThrow(() -> new ParentNotFoundException(paymentRequest.getParentId()));
            Student student = studentService.getStudentById(paymentRequest.getStudentId())
                    .orElseThrow(() -> new StudentNotFoundException(paymentRequest.getStudentId()));

            BigDecimal adjustedAmount = safe(paymentRequest.getPaymentAmount())
                    .multiply(BigDecimal.ONE.add(safe(paymentRequest.getFeeOrDiscountRate())));

            student.setBalance(safe(student.getBalance()).add(safe(adjustedAmount)));
            parent.setBalance(safe(parent.getBalance()).subtract(safe(adjustedAmount)));

            if (student.getParents().size() > 1) {
                for (Parent otherParent : student.getParents()) {
                    if (!otherParent.equals(parent)) {
                        otherParent.setBalance(safe(otherParent.getBalance()).subtract(safe(adjustedAmount)));
                        parentService.saveParent(otherParent);
                    }
                }
            }

            Payment payment = paymentMapper.paymentRequestToPayment(paymentRequest.getPaymentAmount(), student, parent);
            paymentRepository.save(payment);

            logger.info("Payment processed successfully: PaymentId={}", payment.getId());


//            PaymentResponse paymentResponse = paymentMapper.paymentToPaymentResponse(payment);
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setAdjustedAmount(adjustedAmount);
            paymentResponse.setPaymentId(payment.getId());
            paymentResponse.setParentName(parent.getName());
            paymentResponse.setStudentName(student.getName());
            paymentResponse.setUpdatedStudentBalance(student.getBalance());
            paymentResponse.setUpdatedParentBalance(parent.getBalance());

            return paymentResponse;

        } catch (Exception e) {
            logger.error("Payment failed for Parent={}, Student={}, Error={}",
                    paymentRequest.getParentId(), paymentRequest.getStudentId(), e.getMessage(), e);
            throw e;
        }
    }

    private BigDecimal safe(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }


}
