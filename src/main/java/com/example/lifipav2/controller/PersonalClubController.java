package com.example.lifipav2.controller;

import com.example.lifipav2.dto.PersonalClubDTO;
import com.example.lifipav2.exception.AlreadyExistsException;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.service.IPersonalClubService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/personal")
public class PersonalClubController {

    private final IPersonalClubService personalClubService;

    public PersonalClubController(IPersonalClubService personalClubService) {
        this.personalClubService = personalClubService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarPersonal(@Valid @RequestBody PersonalClubDTO personalClubDTO) throws ResourceNotFoundException, AlreadyExistsException {
        personalClubService.create(personalClubDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Registro ingresado correctamente");
    }

    @GetMapping("/listar")
    public Set<PersonalClubDTO> listarPersonal() throws ResourceNotFoundException {
        return personalClubService.readAll();
    }

    @GetMapping("/{id}")
    public PersonalClubDTO findById(@PathVariable Long id) throws ResourceNotFoundException {
        return personalClubService.read(id);
    }

    @GetMapping
    public PersonalClubDTO findByDni(@RequestParam(name = "dni") String dni) throws ResourceNotFoundException {
        return personalClubService.findByDni(dni);
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarPersonal(@Valid @RequestBody PersonalClubDTO personalClubDTO) throws ResourceNotFoundException {
        personalClubService.update(personalClubDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Registro actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonal(@PathVariable Long id) throws ResourceNotFoundException {
        personalClubService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Registro eliminado correctamente");
    }

    @PatchMapping("/desvincular/{id}")
    public ResponseEntity<?> desvincularPersonal(@PathVariable Long id) throws ResourceNotFoundException{
        personalClubService.desvincularClub(id);
        return ResponseEntity.status(HttpStatus.OK).body("Registro desvinculado correctamente");
    }
}
