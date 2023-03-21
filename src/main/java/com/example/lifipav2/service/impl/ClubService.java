package com.example.lifipav2.service.impl;

import com.example.lifipav2.dto.ClubDTO;
import com.example.lifipav2.dto.JugadorDTO;
import com.example.lifipav2.dto.PersonalClubDTO;
import com.example.lifipav2.exception.AlreadyExistsException;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.model.Club;
import com.example.lifipav2.model.Jugador;
import com.example.lifipav2.model.PersonalClub;
import com.example.lifipav2.repository.IClubRepository;
import com.example.lifipav2.service.ICategoriaService;
import com.example.lifipav2.service.IClubService;
import com.example.lifipav2.service.IDomicilioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClubService implements IClubService {

    private static final Logger LOGGER = LogManager.getLogger(ClubService.class);
    private final IClubRepository clubRepository;
    private final IDomicilioService domicilioService;
    private final ICategoriaService categoriaService;
    @Autowired
    private ObjectMapper mapper;

    public ClubService(IClubRepository clubRepository, IDomicilioService domicilioService, ICategoriaService categoriaService) {
        this.clubRepository = clubRepository;
        this.domicilioService = domicilioService;
        this.categoriaService = categoriaService;
    }


    @Override
    public void create(ClubDTO clubDTO) throws ResourceNotFoundException, AlreadyExistsException {
        LOGGER.info("Llamado a createClub : " + clubDTO);
        Optional<Club> clubEncontrado = clubRepository.findByNombre(clubDTO.getNombre());
        if (clubEncontrado.isPresent()) {
            LOGGER.error("Club registrado : " + clubDTO.getNombre());
            throw new AlreadyExistsException("Usuario registrado : " + clubDTO.getNombre());
        }
        clubDTO.setCategorias(categoriaService.validateCategories(clubDTO.getGeneros()));
        LOGGER.debug("Se valida domicilio/fecha inscripcion : " + clubDTO);
        clubDTO.setDomicilio(domicilioService.create(clubDTO.getDomicilio()));
        LOGGER.debug("Se valida el domicilio : " + clubDTO.getDomicilio());
        Club club = mapper.convertValue(clubDTO, Club.class);
        LOGGER.debug("Se crea registro en la bd : " + clubRepository.save(club));
    }

    @Override
    public ClubDTO read(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a readClub : " + id);
        Optional<Club> club = clubRepository.findById(id);
        if (club.isPresent()) {
            ClubDTO clubDTO = mapper.convertValue(club.get(), ClubDTO.class);
            LOGGER.debug("Se obtiene : " + clubDTO);
            return clubDTO;
        }
        LOGGER.error("Registro no encontrado, id " + id);
        throw new ResourceNotFoundException("No se encontro el club : " + id);
    }

    @Override
    public Set<ClubDTO> readAll() throws ResourceNotFoundException {
        LOGGER.info("Llamado a readAllClub");
        List<Club> clubes = clubRepository.findAll();
        LOGGER.debug("Se obtiene : " + clubes);
        if (!clubes.isEmpty()) {
            Set<ClubDTO> clubesDTO = new HashSet<>();
            for (Club club : clubes) {
                clubesDTO.add(mapper.convertValue(club, ClubDTO.class));
            }
            LOGGER.debug("Se mappean a dto : " + clubesDTO);
            return clubesDTO;
        }
        LOGGER.error("No hay clubes registrados");
        throw new ResourceNotFoundException("No hay clubes registrados");

    }

    @Override
    public Set<PersonalClubDTO> personalXClub(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a personalXClub : " + id);
        Set<PersonalClub> personalClubs = clubRepository.findById(id).get().getPersonal();
        LOGGER.debug("Se obtiene : " + personalClubs);
        if (!personalClubs.isEmpty()) {
            Set<PersonalClubDTO> personal = personalClubs.stream().map(p -> mapper.convertValue(p, PersonalClubDTO.class)).collect(Collectors.toSet());
            LOGGER.debug("Se mapean a dto : " + personal);
            return personal;
        }
        LOGGER.error("No hay personal registrado : " + id);
        throw new ResourceNotFoundException("No hay personal registrado : " + id);
    }

    @Override
    public Set<JugadorDTO> jugadorXClub(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a jugadorXClub : " + id);
        Set<Jugador> jugadores = clubRepository.findById(id).get().getJugadores();
        LOGGER.debug("Se obtiene : " + jugadores);
        if (!jugadores.isEmpty()) {
            Set<JugadorDTO> jugadorDTOS = jugadores.stream().map(j -> mapper.convertValue(j, JugadorDTO.class)).collect(Collectors.toSet());
            LOGGER.debug("Se mapean a dto : " + jugadorDTOS);
            return jugadorDTOS;
        }
        LOGGER.error("No hay jugadores registrados : " + id);
        throw new ResourceNotFoundException("No hay jugadores registrados : " + id);
    }

    @Override
    public void update(ClubDTO clubDTO) throws ResourceNotFoundException {
        LOGGER.info("Llamado a updateClub : " + clubDTO);
        Optional<Club> clubBuscado = clubRepository.findById(clubDTO.getId());
        if (clubBuscado.isPresent()) {
            if (!Objects.equals(clubBuscado.get().getGeneros(), clubDTO.getGeneros())) {
                clubDTO.setCategorias(categoriaService.validateCategories(clubDTO.getGeneros()));
            }
            domicilioService.update(clubDTO.getDomicilio());
            LOGGER.debug("Se valida domicilio/fecha/categorias : " + clubDTO);
            clubBuscado = Optional.ofNullable(mapper.convertValue(clubDTO, Club.class));
            LOGGER.debug("Se actualiza en la bd : " + clubRepository.save(clubBuscado.get()));
        } else {
            LOGGER.error(("Registro no encontrado, id : " + clubDTO.getId()));
            throw new ResourceNotFoundException(("No se encontro club : " + clubDTO.getId()));
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a deleteClub : " + id);
        Optional<Club> clubEncontrado = clubRepository.findById(id);
        LOGGER.debug("Se obtiene : " + clubEncontrado);
        if (clubEncontrado.isEmpty()) {
            LOGGER.error(("Registro no encontrado, id : " + id));
            throw new ResourceNotFoundException(("No se encontro club : " + id));
        }
        clubRepository.deleteById(id);
        LOGGER.debug("Se elimina : " + id);
    }

    @Override
    public ClubDTO findByNombre(String nombre) throws ResourceNotFoundException {
        LOGGER.info("Lamado a findNombreClub : " + nombre);
        Optional<Club> clubEncontrado = clubRepository.findByNombre(nombre);
        if (clubEncontrado.isPresent()) {
            ClubDTO clubDTO = mapper.convertValue(clubEncontrado.get(), ClubDTO.class);
            LOGGER.debug("Se obtiene : " + clubDTO);
            return clubDTO;
        }
        LOGGER.error(("Registro no encontrado, nombre : " + nombre));
        throw new ResourceNotFoundException(("No se encontro club : " + nombre));
    }
}
