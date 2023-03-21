package com.example.lifipav2.repository;

import com.example.lifipav2.model.PersonalClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPersonalClubRepository extends JpaRepository<PersonalClub, Long> {

    Optional<PersonalClub> findByDni(String dni);
}
