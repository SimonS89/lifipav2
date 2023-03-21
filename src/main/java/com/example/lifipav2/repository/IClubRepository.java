package com.example.lifipav2.repository;

import com.example.lifipav2.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClubRepository extends JpaRepository<Club, Long> {
    @Query("SELECT c FROM Club c WHERE c.nombre = ?1")
    Optional<Club> findByNombre(String nombre);

}
