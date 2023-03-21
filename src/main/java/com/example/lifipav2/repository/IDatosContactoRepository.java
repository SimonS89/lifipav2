package com.example.lifipav2.repository;

import com.example.lifipav2.model.DatosContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDatosContactoRepository extends JpaRepository<DatosContacto,Long> {
}
