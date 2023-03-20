package com.example.lifipav2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_sequence")
    @SequenceGenerator(name = "categoria_sequence", sequenceName = "categoria_sequence", allocationSize = 1)
    @Column(name = "id_categoria")
    private Long id;
    @NotBlank(message = "Por favor ingrese el nombre de la categoria")
    @Size(min = 4, message = "El nombre de la categoria debe tener al menos 4 caracteres")
    @Column(unique = true)
    private String nombre;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha limite habilitacion para jugar no puede ser nula")
    @Column(unique = true)
    private LocalDate fechaHabilitada;
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Jugador> jugadores;
    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Club> clubes;

    public Categoria() {
    }

    public Categoria(String nombre, LocalDate fechaHabilitada, Set<Jugador> jugadores, Set<Club> clubes) {
        this.nombre = nombre;
        this.fechaHabilitada = fechaHabilitada;
        this.jugadores = jugadores;
        this.clubes = clubes;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaHabilitada() {
        return fechaHabilitada;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public void setFechaHabilitada(LocalDate fechaHabilitada) {
        this.fechaHabilitada = fechaHabilitada;
    }

    public Set<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(Set<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Set<Club> getClubes() {
        return clubes;
    }

    public void setClubes(Set<Club> clubes) {
        this.clubes = clubes;
    }
}
