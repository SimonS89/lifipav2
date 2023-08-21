package com.example.lifipav2.service.impl;

import com.example.lifipav2.exception.AlreadyExistsException;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.model.UserEntity;
import com.example.lifipav2.repository.UserEntityRepository;
import com.example.lifipav2.service.IUserEntityService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserEntityService implements IUserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntityService(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(UserEntity userEntity) throws ResourceNotFoundException, AlreadyExistsException {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntityRepository.save(userEntity);
    }

    @Override
    public UserEntity read(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public Set<UserEntity> readAll() throws ResourceNotFoundException {
        return new HashSet<>(userEntityRepository.findAll());
    }

    @Override
    public void update(UserEntity userEntity) throws ResourceNotFoundException {

    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {

    }
}
