package com.example.lifipav2.controller;

import com.example.lifipav2.dto.ClubDTO;
import com.example.lifipav2.dto.PersonalClubDTO;
import com.example.lifipav2.exception.AlreadyExistsException;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.service.IClubService;
import com.example.lifipav2.service.IPersonalClubService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/clubes")
public class ClubController {

    private final IClubService clubService;
    private final IPersonalClubService personalClubService;

    public ClubController(IClubService clubService,IPersonalClubService personalClubService) {
        this.clubService = clubService;
        this.personalClubService = personalClubService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarClub(@Valid @RequestBody ClubDTO clubDTO) throws ResourceNotFoundException, AlreadyExistsException {
        clubService.create(clubDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Registro ingresado correctamente");
    }

    @GetMapping("/listar")
    public Set<ClubDTO> listarClubes() throws ResourceNotFoundException {
        return clubService.readAll();
    }

    @GetMapping("/{id}")
    public ClubDTO findById(@PathVariable Long id) throws ResourceNotFoundException {
        return clubService.read(id);
    }

    @GetMapping
    public ClubDTO findByNombre(@RequestParam(name = "nombre") String nombre) throws ResourceNotFoundException {
        String nombreCorrecto = nombre.replace("_", " ");
        return clubService.findByNombre(nombreCorrecto);
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarClub(@Valid @RequestBody ClubDTO clubDTO) throws ResourceNotFoundException {
        clubService.update(clubDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Registro actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClub(@PathVariable Long id) throws ResourceNotFoundException {
//        clubService.delete(id);
        personalClubService.deleteClub(id);
        return ResponseEntity.status(HttpStatus.OK).body("Registro eliminado correctamente");
    }

    @GetMapping("/personal/{id}")
    public Set<PersonalClubDTO> personalXClub(@PathVariable Long id) throws ResourceNotFoundException {
        return clubService.personalXClub(id);
    }

}
