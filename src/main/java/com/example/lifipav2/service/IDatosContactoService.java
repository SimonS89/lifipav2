package com.example.lifipav2.service;

import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.model.DatosContacto;


public interface IDatosContactoService {
    DatosContacto create(DatosContacto datosContacto) throws ResourceNotFoundException;

    DatosContacto read(Long id) throws ResourceNotFoundException;

    void update(DatosContacto datosContacto) throws ResourceNotFoundException;

    void validateDatosContacto(DatosContacto datosContacto) throws ResourceNotFoundException;
}
