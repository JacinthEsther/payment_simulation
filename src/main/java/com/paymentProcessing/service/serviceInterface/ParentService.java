package com.paymentProcessing.service.serviceInterface;

import com.paymentProcessing.entities.Parent;

import java.util.Optional;
import java.util.UUID;

public interface ParentService {
    Optional<Parent> getParentById(UUID parentId);
    Parent saveParent(Parent parent);
}
