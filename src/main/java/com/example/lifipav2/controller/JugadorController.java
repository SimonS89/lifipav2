package com.example.lifipav2.controller;

import com.example.lifipav2.dto.JugadorDTO;
import com.example.lifipav2.exception.AlreadyExistsException;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.service.IJugadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/jugador")
public class JugadorController {

    private final IJugadorService jugadorService;

    public JugadorController(IJugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarJugador(@Valid @RequestBody JugadorDTO jugadorDTO) throws AlreadyExistsException, ResourceNotFoundException {
        jugadorService.create(jugadorDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Registro ingresado correctamente");
    }

    @GetMapping("/listar")
    public Set<JugadorDTO> listarJugadores() throws ResourceNotFoundException {
        return jugadorService.readAll();
    }

    @GetMapping("/{id}")
    public JugadorDTO findById(@PathVariable Long id) throws ResourceNotFoundException {
        return jugadorService.read(id);
    }

    @GetMapping
    public JugadorDTO findByDni(@RequestParam(name = "dni") String dni) throws ResourceNotFoundException {
        return jugadorService.findByDni(dni);
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarJugador(@Valid @RequestBody JugadorDTO jugadorDTO) throws ResourceNotFoundException {
        jugadorService.update(jugadorDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Registro actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJugador(@PathVariable Long id) throws ResourceNotFoundException {
        jugadorService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Registro eliminado correctamente");
    }

    @PatchMapping("/desvincular/{id}")
    public ResponseEntity<?> desvincularJugador(@PathVariable Long id) throws ResourceNotFoundException {
        jugadorService.desvincularClub(id);
        return ResponseEntity.status(HttpStatus.OK).body("Registro desvinculado correctamente");
    }

    @GetMapping("/categoriaclub")
    public Set<JugadorDTO> filtrarPorCategoriaClub(@RequestParam(name = "club") Long idClub, @RequestParam(name = "categoria") Long idCategoria) throws ResourceNotFoundException {
        return jugadorService.jugadorXClubXCategoria(idClub, idCategoria);
    }
}
