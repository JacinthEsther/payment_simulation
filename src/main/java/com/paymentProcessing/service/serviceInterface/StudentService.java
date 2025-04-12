package com.paymentProcessing.service.serviceInterface;

import com.paymentProcessing.entities.Student;

import java.util.Optional;
import java.util.UUID;

public interface StudentService {
    Optional<Student> getStudentById(UUID studentId);
    Student saveStudent(Student student);
}
