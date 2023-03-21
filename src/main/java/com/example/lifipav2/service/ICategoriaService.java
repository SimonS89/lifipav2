package com.example.lifipav2.service;

import com.example.lifipav2.dto.CategoriaDTO;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.model.Categoria;
import com.example.lifipav2.util.Genero;

import java.util.List;
import java.util.Set;

public interface ICategoriaService extends IModelService<CategoriaDTO> {
    void createAll(List<CategoriaDTO> categorias);
    Set<Categoria> validateCategories(List<Genero> generos) throws ResourceNotFoundException;

    CategoriaDTO findByNombre(String nombre) throws ResourceNotFoundException;
}
