package com.example.lifipav2.service.impl;

import com.example.lifipav2.dto.CategoriaDTO;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.model.Categoria;
import com.example.lifipav2.repository.ICategoriaRepository;
import com.example.lifipav2.service.ICategoriaService;
import com.example.lifipav2.util.Genero;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoriaService implements ICategoriaService {

    private static final Logger LOGGER = LogManager.getLogger(CategoriaService.class);
    private final ICategoriaRepository categoriaRepository;
    @Autowired
    private ObjectMapper mapper;

    public CategoriaService(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void create(CategoriaDTO categoriaDTO) {
        LOGGER.info("Llamado a createCategoria : " + categoriaDTO);
        Categoria categoria = mapper.convertValue(categoriaDTO, Categoria.class);
        categoriaRepository.save(categoria);
        LOGGER.debug("Se crea un registro en la bd : " + categoria);
    }

    @Override
    public CategoriaDTO read(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a readCategoria : " + id);
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            CategoriaDTO categoriaDTO = mapper.convertValue(categoria.get(), CategoriaDTO.class);
            LOGGER.debug("Se obtiene : " + categoriaDTO);
            return categoriaDTO;
        }
        LOGGER.error("Registro no encontrado, id " + id);
        throw new ResourceNotFoundException("No se encontro la categoria : " + id);
    }

    @Override
    public CategoriaDTO findByNombre(String nombre) throws ResourceNotFoundException {
        LOGGER.info("Llamado a findNombreCategoria : " + nombre);
        Optional<Categoria> categoria = categoriaRepository.findByNombre(nombre);
        if (categoria.isPresent()) {
            CategoriaDTO categoriaDTO = mapper.convertValue(categoria.get(), CategoriaDTO.class);
            LOGGER.debug("Se obtiene : " + categoriaDTO);
            return categoriaDTO;
        }
        LOGGER.error("Registro no encontrado, nombre " + nombre);
        throw new ResourceNotFoundException("No se encontro la categoria : " + nombre);
    }

    @Override
    public Set<CategoriaDTO> readAll() throws ResourceNotFoundException {
        LOGGER.info("Llamado a readAllCategoria");
        List<Categoria> categorias = categoriaRepository.findAll();
        Set<CategoriaDTO> categoriasDTO = new HashSet<>();
        LOGGER.debug("Se obtiene : " + categorias);
        if (!categorias.isEmpty()) {
            for (Categoria categoria : categorias) {
                categoriasDTO.add(mapper.convertValue(categoria, CategoriaDTO.class));
            }
            LOGGER.debug("Se mappean a dto : " + categoriasDTO);
            return categoriasDTO;
        }
        LOGGER.error("No hay categorias registradas");
        throw new ResourceNotFoundException("No hay categorias registradas");
    }

    @Override
    public void update(CategoriaDTO categoriaDTO) throws ResourceNotFoundException {
        LOGGER.info("Llamado a updateCategoria : " + categoriaDTO);
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(categoriaDTO.getId());
        LOGGER.debug("Se obtiene : " + categoriaEncontrada);
        if (categoriaEncontrada.isEmpty()) {
            LOGGER.error("Registro no encontrado, id : " + categoriaDTO.getId());
            throw new ResourceNotFoundException(("No se encontro la categoria : " + categoriaDTO.getId()));
        }
        Categoria categoriaActualizada = categoriaRepository.save(mapper.convertValue(categoriaDTO, Categoria.class));
        LOGGER.debug("Registro actualizado : " + categoriaActualizada);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        LOGGER.info("Llamado a deleteCategoria : " + id);
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);
        LOGGER.debug("Se obtiene : " + categoriaEncontrada);
        if (categoriaEncontrada.isEmpty()) {
            LOGGER.error("Registro no encontrado, id : " + id);
            throw new ResourceNotFoundException(("No se encontro la categoria : " + id));
        }
        categoriaRepository.deleteById(id);
        LOGGER.debug("Se elimina : " + id);
    }

    @Override
    public void createAll(List<CategoriaDTO> categorias) {
        LOGGER.info("Llamado a createAllCategoria : " + categorias);
        for (CategoriaDTO categoriaDTO : categorias) {
            categoriaRepository.save(mapper.convertValue(categoriaDTO, Categoria.class));
        }
    }

    @Override
    public Set<Categoria> validateCategories(List<Genero> generos) throws ResourceNotFoundException {
        LOGGER.info("Llamado al metodo validateCategorias : "+generos);
        Set<Categoria> categorias;
        if (generos.contains(Genero.FEMENINO) || generos.contains(Genero.MASCULINO)) {
            Set<CategoriaDTO> categoriasDTO = this.readAll();
            categorias = categoriasDTO.stream().map(c -> mapper.convertValue(c, Categoria.class)).collect(Collectors.toSet());
            if (generos.contains(Genero.MASCULINO) && !generos.contains(Genero.FEMENINO)) {
                categorias = categorias.stream().filter(c -> c.getNombre().startsWith("2")).collect(Collectors.toSet());
            } else if (generos.contains(Genero.FEMENINO) && !generos.contains(Genero.MASCULINO)) {
                categorias = categorias.stream().filter(c -> !c.getNombre().startsWith("2")).collect(Collectors.toSet());
            }
        } else {
            throw new ResourceNotFoundException("Genero ingresado es incorrecto");
        }
        return categorias;
    }
}
