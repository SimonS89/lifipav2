package com.example.lifipav2.service;

import com.example.lifipav2.dto.ClubDTO;
import com.example.lifipav2.dto.JugadorDTO;
import com.example.lifipav2.dto.PersonalClubDTO;
import com.example.lifipav2.exception.ResourceNotFoundException;

import java.util.Set;

public interface IClubService extends IModelService<ClubDTO>{
    ClubDTO findByNombre(String nombre) throws ResourceNotFoundException;
    Set<PersonalClubDTO> personalXClub(Long id)throws ResourceNotFoundException;
    Set<JugadorDTO> jugadorXClub(Long id) throws ResourceNotFoundException;
}
