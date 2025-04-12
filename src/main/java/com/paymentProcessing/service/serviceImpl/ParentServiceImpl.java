package com.paymentProcessing.service.serviceImpl;

import com.paymentProcessing.entities.Parent;
import com.paymentProcessing.repositories.ParentRepository;
import com.paymentProcessing.service.serviceInterface.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;

    @Autowired
    public ParentServiceImpl(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    public Optional<Parent> getParentById(UUID parentId) {
        return parentRepository.findById(parentId);
    }

    @Override
    public Parent saveParent(Parent parent) {
        return parentRepository.save(parent);
    }
}
