package com.example.lifipav2.service;

import com.example.lifipav2.dto.PersonalClubDTO;
import com.example.lifipav2.exception.ResourceNotFoundException;

public interface IPersonalClubService extends IModelService<PersonalClubDTO> {
    PersonalClubDTO findByDni(String dni) throws ResourceNotFoundException;
    void deleteClub(long id) throws ResourceNotFoundException;
    public void desvincularClub(Long id) throws ResourceNotFoundException;
}
