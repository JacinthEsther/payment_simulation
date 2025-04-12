package com.paymentProcessing.repositories;

import com.paymentProcessing.entities.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParentRepository extends JpaRepository<Parent, UUID> {}
