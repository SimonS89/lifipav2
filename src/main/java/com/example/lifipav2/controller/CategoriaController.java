package com.example.lifipav2.controller;

import com.example.lifipav2.dto.CategoriaDTO;
import com.example.lifipav2.exception.AlreadyExistsException;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.service.ICategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final ICategoriaService categoriaService;

    public CategoriaController(ICategoriaService categoriaService) {

        this.categoriaService = categoriaService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws ResourceNotFoundException, AlreadyExistsException {
        categoriaService.create(categoriaDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Registro ingresado correctamente");
    }

    @PostMapping("/guardartodas")
    public ResponseEntity<?> guardarCategorias(@Valid @RequestBody List<CategoriaDTO> categoriasDto) {
        categoriaService.createAll(categoriasDto);
        return ResponseEntity.status(HttpStatus.OK).body("Registros ingresados correctamente");
    }

    @GetMapping("/listar")
    public Set<CategoriaDTO> listarCategorias() throws ResourceNotFoundException {
        return categoriaService.readAll();
    }

    @GetMapping("/{id}")
    public CategoriaDTO findById(@PathVariable Long id) throws ResourceNotFoundException {
        return categoriaService.read(id);
    }

    @GetMapping()
    public CategoriaDTO findByNombre(@RequestParam(name = "nombre") String nombre) throws ResourceNotFoundException {
        return categoriaService.findByNombre(nombre);
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws ResourceNotFoundException {
        categoriaService.update(categoriaDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Registro actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable Long id) throws ResourceNotFoundException {
        categoriaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Registro eliminado correctamente");
    }
public void ad(){
    System.out.println("asd");
}
}
