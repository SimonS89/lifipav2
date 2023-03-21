package com.example.lifipav2.service;

import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.model.Domicilio;

public interface IDomicilioService {

    Domicilio create(Domicilio domicilio) throws ResourceNotFoundException;

    Domicilio read(Long id) throws ResourceNotFoundException;

    Domicilio update(Domicilio domicilio) throws ResourceNotFoundException;

    void validateDomicilio(Domicilio domicilio) throws ResourceNotFoundException;
}
