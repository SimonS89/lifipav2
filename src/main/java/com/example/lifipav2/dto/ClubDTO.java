package com.example.lifipav2.dto;

import com.example.lifipav2.model.Categoria;
import com.example.lifipav2.model.Domicilio;
import com.example.lifipav2.util.Genero;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ClubDTO {
    private Long id;
    @NotBlank(message = "Por favor ingrese el nombre de la institucion")
    @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres")
    private String nombre;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de fundacion de la institucion no puede ser nula")
    private LocalDate fechaFundacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInscrpcion;
    @NotNull(message = "El domicilio de la institucion no puede ser nulo")
    private Domicilio domicilio;
    @NotNull(message = "El club debe tener al menos las categorias de un genero")
    @Enumerated(EnumType.STRING)
    private List<Genero> generos;
    private Set<Categoria> categorias;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public LocalDate getFechaInscrpcion() {
        return fechaInscrpcion;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public void setFechaInscrpcion(LocalDate fechaInscrpcion) {
        this.fechaInscrpcion = fechaInscrpcion;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    @Override
    public String toString() {
        return "ClubDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaFundacion=" + fechaFundacion +
                ", fechaInscrpcion=" + fechaInscrpcion +
                ", domicilio=" + domicilio +
                ", generos=" + generos +
                ", categorias=" + categorias +
                '}';
    }
}
