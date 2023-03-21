package com.example.lifipav2.service.impl;

import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.model.Domicilio;
import com.example.lifipav2.repository.IDomicilioRepository;
import com.example.lifipav2.service.IDomicilioService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class DomicilioService implements IDomicilioService {

    private static final Logger LOGGER = LogManager.getLogger(DomicilioService.class);
    private final IDomicilioRepository domicilioRepository;
    @Autowired
    private Validator validator;

    public DomicilioService(IDomicilioRepository domicilioRepository) {
        this.domicilioRepository = domicilioRepository;
    }

    @Override
    public Domicilio create(Domicilio domicilio) throws ResourceNotFoundException {
        LOGGER.info("Llamado a createDomicilio : " + domicilio);
        validateDomicilio(domicilio);
        Domicilio domicilioRegistrado = domicilioRepository.save(domicilio);
        LOGGER.debug("Registro creado : " + domicilioRegistrado);
        return domicilioRegistrado;
    }

    @Override
    public Domicilio read(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a readDomicilio : " + id);
        Optional<Domicilio> domicilio = domicilioRepository.findById(id);
        if (domicilio.isPresent()) {
            LOGGER.debug("Se obtiene : " + domicilio.get());
            return domicilio.get();
        }
        LOGGER.error("Registro no encontrado, id " + id);
        throw new ResourceNotFoundException("No se encontro el domicilio con el id : " + id);
    }

    @Override
    public Domicilio update(Domicilio domicilio) throws ResourceNotFoundException {
        LOGGER.info("Llamado a updateDomicilio : " + domicilio);
        validateDomicilio(domicilio);
        Optional<Domicilio> domicilioEncontrado = domicilioRepository.findById(domicilio.getId());
        LOGGER.debug("Se obtiene : " + domicilioEncontrado);
        if (domicilioEncontrado.isPresent()) {
            if (!domicilio.equals(domicilioEncontrado.get())) {
                Domicilio domicilioActualizado = domicilioRepository.save(domicilio);
                LOGGER.debug("Registro actualizado : " + domicilioActualizado);
                return domicilioActualizado;
            }
        }
        LOGGER.error("Registro no encontrado, id : " + domicilio.getId());
        throw new ResourceNotFoundException("No se encontro el domicilio con el id : " + domicilio.getId());
    }

    @Override
    public void validateDomicilio(Domicilio domicilio) throws ResourceNotFoundException {
        Set<ConstraintViolation<Domicilio>> violations = validator.validate(domicilio);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder(domicilio.getClass().getSimpleName()+", ");
            for (ConstraintViolation<Domicilio> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage()).append(", ");
            }
            throw new ResourceNotFoundException(sb.toString());
        }
    }
}
