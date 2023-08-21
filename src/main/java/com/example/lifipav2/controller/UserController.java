package com.example.lifipav2.controller;

import com.example.lifipav2.dto.request.AuthRequestDTO;
import com.example.lifipav2.exception.AlreadyExistsException;
import com.example.lifipav2.exception.ResourceNotFoundException;
import com.example.lifipav2.model.UserEntity;
import com.example.lifipav2.security.service.JwtService;
import com.example.lifipav2.service.IUserEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final IUserEntityService userEntityService;
    private final JwtService jwtService;

    public UserController(IUserEntityService userEntityService, JwtService jwtService) {
        this.userEntityService = userEntityService;
        this.jwtService = jwtService;
    }

    @PostMapping("/create")
    private ResponseEntity<String> createUser(@RequestBody UserEntity userEntity) throws AlreadyExistsException, ResourceNotFoundException {
        userEntityService.create(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created Successfully");
    }

    @GetMapping("/listar")
    public ResponseEntity<Set<UserEntity>> listarUsers() throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userEntityService.readAll());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(jwtService.generateToken(authRequestDTO.getUsername()));
    }
}
