package com.example.lifipav2.service;

import com.example.lifipav2.exception.AlreadyExistsException;
import com.example.lifipav2.exception.ResourceNotFoundException;

import java.util.Set;

public interface IModelService<T> {

    void create(T t) throws ResourceNotFoundException, AlreadyExistsException;

    T read(Long id)throws ResourceNotFoundException;

    Set<T> readAll()throws ResourceNotFoundException;

    void update(T t) throws ResourceNotFoundException;

    void delete(Long id)throws ResourceNotFoundException;

}
