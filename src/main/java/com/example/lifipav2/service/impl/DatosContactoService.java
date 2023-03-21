package com.example.lifipav2.service.impl;

import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.model.DatosContacto;
import com.example.lifipav2.repository.IDatosContactoRepository;
import com.example.lifipav2.service.IDatosContactoService;
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
public class DatosContactoService implements IDatosContactoService {

    private static final Logger LOGGER = LogManager.getLogger(DatosContactoService.class);
    private final IDatosContactoRepository datosContactoRepository;
    @Autowired
    private Validator validator;

    public DatosContactoService(IDatosContactoRepository datosContactoRepository) {
        this.datosContactoRepository = datosContactoRepository;
    }

    @Override
    public DatosContacto create(DatosContacto datosContacto) throws ResourceNotFoundException {
        LOGGER.info("Llamando a createDatosContacto : " + datosContacto);
        validateDatosContacto(datosContacto);
        DatosContacto datosRegistrados = datosContactoRepository.save(datosContacto);
        LOGGER.debug("Registro creado : " + datosRegistrados);
        return datosRegistrados;
    }

    @Override
    public DatosContacto read(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a readDatosContacto : " + id);
        Optional<DatosContacto> datosContacto = datosContactoRepository.findById(id);
        if (datosContacto.isPresent()) {
            LOGGER.debug("Se obtiene : " + datosContacto.get());
            return datosContacto.get();
        }
        LOGGER.error("Registro no encontrado, id : " + id);
        throw new ResourceNotFoundException("No se encontraron los datos con el id " + id);
    }

    @Override
    public void update(DatosContacto datosContacto) throws ResourceNotFoundException {
        LOGGER.info("Llamado a updateDatosContacto : " + datosContacto);
        validateDatosContacto(datosContacto);
        Optional<DatosContacto> datosEncontrados = datosContactoRepository.findById(datosContacto.getId());
        LOGGER.debug("Se obtiene : " + datosEncontrados);
        if (datosEncontrados.isEmpty()) {
            LOGGER.error("Registro no encontrado, id : " + datosContacto.getId());
            throw new ResourceNotFoundException("No se encontraron los datos de contacto para el id " + datosContacto.getId());
        }
        if (!datosContacto.equals(datosEncontrados.get())) {
            DatosContacto datosActualizados = datosContactoRepository.save(datosContacto);
            LOGGER.debug("Registro actualizado : " + datosActualizados);
        }

    }

    @Override
    public void validateDatosContacto(DatosContacto datosContacto) throws ResourceNotFoundException {
        Set<ConstraintViolation<DatosContacto>> violations = validator.validate(datosContacto);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder(datosContacto.getClass().getSimpleName() + ", ");
            for (ConstraintViolation<DatosContacto> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage()).append(", ");
            }
            throw new ResourceNotFoundException(sb.toString());
        }
    }
}
