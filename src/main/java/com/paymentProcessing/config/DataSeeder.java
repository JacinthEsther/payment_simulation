package com.paymentProcessing.config;

import com.paymentProcessing.entities.Parent;
import com.paymentProcessing.entities.Student;
import com.paymentProcessing.repositories.ParentRepository;
import com.paymentProcessing.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;

    public DataSeeder(ParentRepository parentRepository, StudentRepository studentRepository) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        Parent parent1 = new Parent();
        parent1.setName("John Doe");
        parent1.setBalance(new BigDecimal("4000.00"));

        Parent parent2 = new Parent();
        parent2.setName("Jane Smith");
        parent2.setBalance(new BigDecimal("3000.00"));

        parentRepository.saveAll(Set.of(parent1, parent2));


        Student student1 = new Student();
        student1.setName("Emily Doe");
        student1.setBalance(new BigDecimal("1500.00"));

        Student student2 = new Student();
        student2.setName("Liam Smith");
        student2.setBalance(new BigDecimal("1800.00"));

        Student student3 = new Student();
        student3.setName("Olivia Doe");
        student3.setBalance(new BigDecimal("1300.00"));


        student1.setParents(Set.of(parent1));
        student2.setParents(Set.of(parent2));
        student3.setParents(Set.of(parent1, parent2));


        studentRepository.saveAll(Set.of(student1, student2, student3));
    }
}

