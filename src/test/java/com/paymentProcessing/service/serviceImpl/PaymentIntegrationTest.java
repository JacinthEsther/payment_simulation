package com.paymentProcessing.service.serviceImpl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentProcessing.dtos.PaymentRequest;
import com.paymentProcessing.entities.Parent;
import com.paymentProcessing.entities.Student;
import com.paymentProcessing.repositories.ParentRepository;
import com.paymentProcessing.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ParentRepository parentRepository;
    @Autowired private StudentRepository studentRepository;

    private UUID parentId;
    private UUID studentId;
    private BigDecimal initialParentBalance;
    private BigDecimal initialStudentBalance;

    @BeforeEach
    void setup() {
        parentRepository.deleteAll();
        studentRepository.deleteAll();

        Parent parent = new Parent();
        parent.setName("Parent A");
        parent.setBalance(BigDecimal.valueOf(1000));
        parent = parentRepository.save(parent);

        Student student = new Student();
        student.setName("Student 2");
        student.setBalance(BigDecimal.ZERO);
        student.setParents(Set.of(parent));
        student = studentRepository.save(student);

        parentId = parent.getId();
        studentId = student.getId();

        initialParentBalance = parent.getBalance();
        initialStudentBalance = student.getBalance();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testPaymentForUniqueStudent() throws Exception {
        BigDecimal paymentAmount = BigDecimal.valueOf(100);
        BigDecimal feeOrDiscountRate = BigDecimal.valueOf(0.1); // 10%

        PaymentRequest request = new PaymentRequest();
        request.setParentId(parentId);
        request.setStudentId(studentId);
        request.setPaymentAmount(paymentAmount);
        request.setFeeOrDiscountRate(feeOrDiscountRate);

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adjustedAmount").value("110.0")); // 100 + 10%


        Parent updatedParent = parentRepository.findById(parentId).orElseThrow();
        Student updatedStudent = studentRepository.findById(studentId).orElseThrow();

        BigDecimal expectedAdjustedAmount = paymentAmount.multiply(BigDecimal.ONE.add(feeOrDiscountRate));


        BigDecimal expectedParentBalance = initialParentBalance.subtract(expectedAdjustedAmount);
        assertEquals(0, expectedParentBalance.compareTo(updatedParent.getBalance()), "Parent balance should be reduced correctly");

        BigDecimal expectedStudentBalance = initialStudentBalance.add(expectedAdjustedAmount);
        assertEquals(0, expectedStudentBalance.compareTo(updatedStudent.getBalance()), "Student balance should be increased correctly");
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testAccessDeniedForNonAdmin() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setParentId(parentId);
        request.setStudentId(studentId);
        request.setPaymentAmount(BigDecimal.valueOf(100));
        request.setFeeOrDiscountRate(BigDecimal.ZERO);

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
}
