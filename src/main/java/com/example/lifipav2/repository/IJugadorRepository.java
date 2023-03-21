package com.example.lifipav2.repository;

import com.example.lifipav2.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IJugadorRepository extends JpaRepository<Jugador, Long> {
    Optional<Jugador> findByDni(String dni);
}
