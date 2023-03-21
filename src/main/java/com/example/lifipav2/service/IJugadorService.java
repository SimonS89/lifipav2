package com.example.lifipav2.service;

import com.example.lifipav2.dto.JugadorDTO;
import com.example.lifipav2.exception.ResourceNotFoundException;

import java.util.Set;

public interface IJugadorService extends IModelService<JugadorDTO> {
    JugadorDTO findByDni(String dni) throws ResourceNotFoundException;

    public void desvincularClub(Long id) throws ResourceNotFoundException;

    public Set<JugadorDTO> jugadorXClubXCategoria(Long idClub, Long idCategoria) throws ResourceNotFoundException;
}
