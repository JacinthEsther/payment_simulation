package com.paymentProcessing.service.serviceImpl;

import com.paymentProcessing.entities.Student;
import com.paymentProcessing.repositories.StudentRepository;
import com.paymentProcessing.service.serviceInterface.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> getStudentById(UUID studentId) {
        return studentRepository.findById(studentId);
    }

    // todo endpoint to get student and parent balance, get all payments
    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
}
