package com.example.lifipav2.service.impl;

import com.example.lifipav2.dto.CategoriaDTO;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.repository.ICategoriaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoriaServiceTest {

    private ICategoriaRepository categoriaRepository = mock(ICategoriaRepository.class);
    private CategoriaService categoriaService = new CategoriaService(categoriaRepository);

    @Test
    public void listarCategorias() throws ResourceNotFoundException {
        when(categoriaRepository.findAll()).thenReturn(new ArrayList<>());
        Set<CategoriaDTO> categorias = new HashSet<>();
        try{
          categorias = categoriaService.readAll();
        }catch (ResourceNotFoundException ex){
            Assertions.assertEquals(categorias.size(),0);
        }
    }
}