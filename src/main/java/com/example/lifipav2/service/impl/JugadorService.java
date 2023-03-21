package com.example.lifipav2.service.impl;

import com.example.lifipav2.dto.ClubPersonalDTO;
import com.example.lifipav2.dto.JugadorDTO;
import com.example.lifipav2.exception.AlreadyExistsException;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.model.Categoria;
import com.example.lifipav2.model.Club;
import com.example.lifipav2.model.Jugador;
import com.example.lifipav2.repository.IJugadorRepository;
import com.example.lifipav2.service.ICategoriaService;
import com.example.lifipav2.service.IClubService;
import com.example.lifipav2.service.IDatosContactoService;
import com.example.lifipav2.service.IJugadorService;
import com.example.lifipav2.util.Genero;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class JugadorService implements IJugadorService {

    private static final Logger LOGGER = LogManager.getLogger(JugadorService.class);
    private final IJugadorRepository jugadorRepository;
    private final ICategoriaService categoriaService;
    private final IClubService clubService;
    private final IDatosContactoService datosContactoService;
    @Autowired
    private ObjectMapper mapper;

    public JugadorService(IJugadorRepository jugadorRepository, ICategoriaService categoriaService, IClubService clubService, IDatosContactoService datosContactoService) {
        this.jugadorRepository = jugadorRepository;
        this.categoriaService = categoriaService;
        this.clubService = clubService;
        this.datosContactoService = datosContactoService;
    }

    @Override
    public void create(JugadorDTO jugadorDTO) throws ResourceNotFoundException, AlreadyExistsException {
        LOGGER.info("Llamado a createJugador : " + jugadorDTO);
        Optional<Jugador> jugadorEncontrado = jugadorRepository.findByDni(jugadorDTO.getDni());
        if (jugadorEncontrado.isPresent()) {
            LOGGER.error("Jugador registrado : " + jugadorDTO.getDni());
            throw new AlreadyExistsException("Jugador registrado : " + jugadorDTO.getDni());
        }
        Club club = mapper.convertValue(clubService.read(jugadorDTO.getClub().getId()), Club.class);
        jugadorDTO.setDatosContacto(datosContactoService.create(jugadorDTO.getDatosContacto()));
        Categoria categoria = mapper.convertValue(categoriaService.read(jugadorDTO.getCategoria().getId()), Categoria.class);
        if (!jugadorCategoriaValidate(jugadorDTO.getFechaNacimiento(), categoria, jugadorDTO.getGenero())) {
            LOGGER.error("Categoria invalida para : " + jugadorDTO.getDni());
            throw new ResourceNotFoundException("Categoria invalida para : " + jugadorDTO.getDni());
        }
        jugadorDTO.setCategoria(categoria);
        Jugador jugador = mapper.convertValue(jugadorDTO, Jugador.class);
        jugador.setClub(club);
        LOGGER.debug("Se crea registro en la bd : " + jugadorRepository.save(jugador));
    }

    @Override
    public JugadorDTO read(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a readJugador : " + id);
        Optional<Jugador> jugador = jugadorRepository.findById(id);
        if (jugador.isPresent()) {
            JugadorDTO jugadorDTO = mapper.convertValue(jugador.get(), JugadorDTO.class);
            LOGGER.debug("Se obtiene : " + jugadorDTO);
            return jugadorDTO;
        }
        LOGGER.error("Registro no encontrado, id " + id);
        throw new ResourceNotFoundException("No se encontro el personal : " + id);
    }

    @Override
    public Set<JugadorDTO> readAll() throws ResourceNotFoundException {
        LOGGER.info("Llamado a readAllJugador");
        List<Jugador> jugadores = jugadorRepository.findAll();
        LOGGER.debug("Se obitne : " + jugadores);
        if (!jugadores.isEmpty()) {
            Set<JugadorDTO> jugadorDTOS = jugadores.stream().map(jugador -> mapper.convertValue(jugador, JugadorDTO.class)).collect(Collectors.toSet());
            LOGGER.debug("Se mapean a dto : " + jugadorDTOS);
            return jugadorDTOS;
        }
        LOGGER.error("No hay jugadores registrados");
        throw new ResourceNotFoundException("No hay jugadores registrados");
    }

    @Override
    public void update(JugadorDTO jugadorDTO) throws ResourceNotFoundException {
        LOGGER.info("Llamado a updateJugador : " + jugadorDTO);
        Optional<Jugador> jugadorBuscado = jugadorRepository.findById(jugadorDTO.getId());
        if (jugadorBuscado.isEmpty()) {
            LOGGER.error("Registro no encontrado, id : " + jugadorDTO.getId());
            throw new ResourceNotFoundException("No se encontro jugador : " + jugadorDTO.getId());
        }
        datosContactoService.update(jugadorDTO.getDatosContacto());

        if (!jugadorDTO.getClub().getId().equals(jugadorBuscado.get().getClub().getId())) {
            Club club = mapper.convertValue(clubService.read(jugadorDTO.getClub().getId()), Club.class);
            jugadorDTO.setClub(mapper.convertValue(club, ClubPersonalDTO.class));
            LOGGER.debug("Se actualiza el club del jugador : " + jugadorBuscado.get());
        }
        Categoria categoria = mapper.convertValue(categoriaService.read(jugadorDTO.getCategoria().getId()), Categoria.class);
        if (jugadorCategoriaValidate(jugadorDTO.getFechaNacimiento(), categoria, jugadorDTO.getGenero())) {
            jugadorDTO.setCategoria(categoria);
            jugadorBuscado = Optional.ofNullable(mapper.convertValue(jugadorDTO, Jugador.class));
            LOGGER.debug("Se actualiza en la bd : " + jugadorRepository.save(jugadorBuscado.get()));
       }else{
            throw new ResourceNotFoundException("Categoria incorrecta");
        }

    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a deleteJugador : " + id);
        Optional<Jugador> jugadorEncontrado = jugadorRepository.findById(id);
        LOGGER.debug("Se obtiene : " + jugadorEncontrado);
        if (jugadorEncontrado.isEmpty()) {
            LOGGER.error("Registro no encontrado, id : " + id);
            throw new ResourceNotFoundException("No se encontro personal club : " + id);
        }
        jugadorRepository.deleteById(id);
        LOGGER.debug("Se elimina : " + id);
    }

    @Override
    public JugadorDTO findByDni(String dni) throws ResourceNotFoundException {
        LOGGER.info("Llamado a findDniJugador : " + dni);
        Optional<Jugador> jugadorEncontrado = jugadorRepository.findByDni(dni);
        LOGGER.debug("Se obtiene : " + jugadorEncontrado);
        if (jugadorEncontrado.isPresent()) {
            JugadorDTO jugadorDTO = mapper.convertValue(jugadorEncontrado, JugadorDTO.class);
            LOGGER.debug("Se obtiene : " + jugadorDTO);
            return jugadorDTO;
        }
        LOGGER.error("Registro no encontrado, dni " + dni);
        throw new ResourceNotFoundException("No se encontro personal : " + dni);
    }

    @Override
    public void desvincularClub(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a desvincularClubJugador : " + id);
        Optional<Jugador> jugadorEncontrado = jugadorRepository.findById(id);
        LOGGER.debug("Se obtiene : " + jugadorEncontrado);
        if (jugadorEncontrado.isEmpty()) {
            LOGGER.error("Registro no encontrado, id " + id);
            throw new ResourceNotFoundException("No se encontro el jugador : " + id);
        }
        jugadorEncontrado.get().setClub(null);
        LOGGER.debug(("Se setea el club a null " + jugadorEncontrado.get()));
        jugadorRepository.save(jugadorEncontrado.get());
    }

    @Override
    public Set<JugadorDTO> jugadorXClubXCategoria(Long idClub,Long idCategoria) throws ResourceNotFoundException {
        Set<JugadorDTO> jugadoresFiltrados = clubService.jugadorXClub(idClub);
        jugadoresFiltrados = jugadoresFiltrados.stream().filter(j->j.getCategoria().getId().equals(idCategoria)).collect(Collectors.toSet());
        return jugadoresFiltrados;
    }

    private boolean jugadorCategoriaValidate(LocalDate nacimiento, Categoria categoria, Genero genero) {
        return nacimiento.isAfter(categoria.getFechaHabilitada().minusDays(1)) && (genero.equals(Genero.MASCULINO) && categoria.getNombre().startsWith("2")) || genero.equals(Genero.FEMENINO) && (!categoria.getNombre().startsWith("2"));
    }
}
