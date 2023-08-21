package com.example.lifipav2.service.impl;

import com.example.lifipav2.dto.ClubDTO;
import com.example.lifipav2.dto.JugadorDTO;
import com.example.lifipav2.dto.PersonalClubDTO;
import com.example.lifipav2.exception.AlreadyExistsException;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.model.Club;
import com.example.lifipav2.model.PersonalClub;
import com.example.lifipav2.repository.IPersonalClubRepository;
import com.example.lifipav2.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class PersonalClubService implements IPersonalClubService {

    private static final Logger LOGGER = LogManager.getLogger(PersonalClubService.class);
    private final IPersonalClubRepository personalClubRepository;
    private final IDatosContactoService datosContactoService;
    private final IClubService clubService;
    private final IDomicilioService domicilioService;
    private final IJugadorService jugadorService;
    @Autowired
    private ObjectMapper mapper;

    public PersonalClubService(IPersonalClubRepository personalClubRepository, IDatosContactoService datosContactoService, IClubService clubService, IDomicilioService domicilioService, IJugadorService jugadorService) {
        this.personalClubRepository = personalClubRepository;
        this.datosContactoService = datosContactoService;
        this.clubService = clubService;
        this.domicilioService = domicilioService;
        this.jugadorService = jugadorService;
    }

    @Override
    public void create(PersonalClubDTO personalClubDTO) throws ResourceNotFoundException, AlreadyExistsException {
        LOGGER.info("Llamado a createPersonal : " + personalClubDTO);
        Optional<PersonalClub> personalClubEncontrado = personalClubRepository.findByDni(personalClubDTO.getDni());
        if (personalClubEncontrado.isPresent()) {
            LOGGER.error("Personal registrado : " + personalClubDTO.getDni());
            throw new AlreadyExistsException("Usuario registrado : " + personalClubDTO.getDni());
        }
        Club club = mapper.convertValue(clubService.read(personalClubDTO.getClub().getId()), Club.class);
        personalClubDTO.setDatosContacto(datosContactoService.create(personalClubDTO.getDatosContacto()));
        personalClubDTO.setDomicilio(domicilioService.create(personalClubDTO.getDomicilio()));
        LOGGER.debug("Se valida la fecha inscripcion,datos contacto,domicilio : " + personalClubDTO);
        PersonalClub personalClub = mapper.convertValue(personalClubDTO, PersonalClub.class);
        personalClub.setClub(club);
        LOGGER.debug("Se crea registro en la bd : " + personalClubRepository.save(personalClub));
    }

    @Override
    public PersonalClubDTO read(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a readPersonalClub + " + id);
        Optional<PersonalClub> personalClub = personalClubRepository.findById(id);
        if (personalClub.isPresent()) {
            PersonalClubDTO personalClubDTO = mapper.convertValue(personalClub.get(), PersonalClubDTO.class);
            LOGGER.debug("Se obtiene : " + personalClubDTO);
            return personalClubDTO;
        }
        LOGGER.error("Registro no encontrado, id " + id);
        throw new ResourceNotFoundException("No se encontro el personal : " + id);
    }

    @Override
    public Set<PersonalClubDTO> readAll() throws ResourceNotFoundException {
        LOGGER.info("Llamado a readAllPersonalClub");
        List<PersonalClub> personalClubs = personalClubRepository.findAll();
        LOGGER.debug("Se obtiene : " + personalClubs);
        if (!personalClubs.isEmpty()) {
            Set<PersonalClubDTO> personalClubDTOS = personalClubs.stream().map(personalClub -> mapper.convertValue(personalClub, PersonalClubDTO.class)).collect(Collectors.toSet());
            LOGGER.debug("Se mapean a dto : " + personalClubDTOS);
            return personalClubDTOS;
        }
        LOGGER.error("No hay personal registrado");
        throw new ResourceNotFoundException("No hay personal registrado");
    }

    @Override
    public void update(PersonalClubDTO personalClubDTO) throws ResourceNotFoundException {
        LOGGER.info("Llamado a updatePersonalClub : " + personalClubDTO);
        Optional<PersonalClub> personalBuscado = personalClubRepository.findById(personalClubDTO.getId());
        if (personalBuscado.isEmpty()) {
            LOGGER.error("Registro no encontrado, id : " + personalClubDTO.getId());
            throw new ResourceNotFoundException("No se encontro personal : " + personalClubDTO.getId());
        }
        domicilioService.update(personalClubDTO.getDomicilio());
        datosContactoService.update(personalClubDTO.getDatosContacto());
        LOGGER.debug("Se actualizan datos contacto y domicilio: " + personalClubDTO);
        Club club = mapper.convertValue(clubService.read(personalClubDTO.getClub().getId()), Club.class);
        if(club!=null){
            personalBuscado = Optional.ofNullable(mapper.convertValue(personalClubDTO, PersonalClub.class));
            personalBuscado.get().setClub(club);
            LOGGER.debug("Se actualiza el club del personal : " + personalBuscado.get());
        }
        LOGGER.debug("Se actualiza en la bd : " + personalClubRepository.save(personalBuscado.get()));
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a deletePersonalClub : " + id);
        Optional<PersonalClub> personalEncontrado = personalClubRepository.findById(id);
        LOGGER.debug("Se obtiene : " + personalEncontrado);
        if (personalEncontrado.isEmpty()) {
            LOGGER.error("Registro no encontrado, id : " + id);
            throw new ResourceNotFoundException("No se encontro personal club : " + id);
        }
        personalClubRepository.deleteById(id);
        LOGGER.debug("Se elimina : " + id);
    }

    @Override
    public PersonalClubDTO findByDni(String dni) throws ResourceNotFoundException {
        LOGGER.info("Llamado a findDniPersonal : " + dni);
        Optional<PersonalClub> personalEncontrado = personalClubRepository.findByDni(dni);
        LOGGER.debug("Se obtiene : " + personalEncontrado);
        if (personalEncontrado.isPresent()) {
            PersonalClubDTO personalClubDTO = mapper.convertValue(personalEncontrado.get(), PersonalClubDTO.class);
            LOGGER.debug("Se obtiene : " + personalClubDTO);
            return personalClubDTO;
        }
        LOGGER.error("Registro no encontrado, dni " + dni);
        throw new ResourceNotFoundException("No se encontro personal : " + dni);
    }

    @Override
    public void desvincularClub(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a desvincularClubPersonal : " + id);
        Optional<PersonalClub> personalEncontrado = personalClubRepository.findById(id);
        LOGGER.debug("Se obtiene : " + personalEncontrado);
        if (personalEncontrado.isEmpty()) {
            LOGGER.error("Registro no encontrado, id " + id);
            throw new ResourceNotFoundException("No se encontro el personal : " + id);
        }
        personalEncontrado.get().setClub(null);
        LOGGER.debug("Se setea el club a null " + personalEncontrado.get());
        personalClubRepository.save(personalEncontrado.get());
    }


    @Override
    public void deleteClub(long id) throws ResourceNotFoundException {
        Optional<ClubDTO> club = Optional.ofNullable(clubService.read(id));
        if (club.isPresent()) {
            try {
                Set<PersonalClubDTO> personalClubs = clubService.personalXClub(id);
                for (PersonalClubDTO personalClubDTO : personalClubs) {
                    desvincularClub(personalClubDTO.getId());
                }
            } catch (ResourceNotFoundException ex) {
                LOGGER.error("No hay personal");
            }
            try {
                Set<JugadorDTO> jugadores = clubService.jugadorXClub(id);
                for (JugadorDTO jugadorDTO : jugadores) {
                    jugadorService.desvincularClub(jugadorDTO.getId());
                }
            } catch (ResourceNotFoundException ex) {
                LOGGER.error("No hay jugadores");
            }
            clubService.delete(id);
        } else {
            LOGGER.error("Registro no encontrado : " + id);
            throw new ResourceNotFoundException("Registro no encontrado : " + id);
        }

    }
}
